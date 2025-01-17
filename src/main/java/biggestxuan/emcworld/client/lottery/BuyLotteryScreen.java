package biggestxuan.emcworld.client.lottery;

/**
 *  EMC WORLD MOD
 *  @Author Biggest_Xuan
 *  2023/05/07
 */

import biggestxuan.emcworld.EMCWorld;
import biggestxuan.emcworld.common.compact.Projecte.EMCHelper;
import biggestxuan.emcworld.common.items.LotteryItem;
import biggestxuan.emcworld.common.network.PacketHandler;
import biggestxuan.emcworld.common.network.toServer.BuyLotteryPacket;
import biggestxuan.emcworld.common.utils.Lottery.Lottery;
import biggestxuan.emcworld.common.utils.Lottery.LotteryMode;
import biggestxuan.emcworld.common.utils.Lottery.LotteryUtils;
import biggestxuan.emcworld.common.utils.MathUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public abstract class BuyLotteryScreen extends Screen {
    protected final List<Integer> numList;
    protected final List<Integer> addList;
    private TextFieldWidget text;
    private boolean lastIsShift = false;
    protected double base = ModList.get().isLoaded("modernui") ? 0.55 : 0.2;

    protected BuyLotteryScreen(String name) {
        super(EMCWorld.tc("screen.lottery."+name));
        numList = new ArrayList<>();
        addList = new ArrayList<>();
    }

    protected abstract LotteryMode getScreenLotteryMode();

    protected abstract boolean renderConfirmButton();

    @Override
    public void init(){
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        super.init();
        getNumButtons().forEach(this::addButton);
        if(renderConfirmButton()){
            ConfirmButton b = new ConfirmButton();
            b.active = false;
            this.addButton(b);
        }
        this.addButton(new RefreshButton());
        this.addButton(new BackButton());
        this.addButton(new UndoButton());
        text = new TextFieldWidget(Minecraft.getInstance().font, (int) (width * 0.25), (int) (height*0.75),100,20,EMCWorld.tc("aaa"));
        text.setMaxLength(5);
        text.setValue("1");
        this.children.add(text);
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks){
        this.renderBackground(matrixStack);
        Minecraft mc = Minecraft.getInstance();
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawCenteredString(matrixStack,mc.font,EMCWorld.tc("tooltip.emcworld.lottery."+getMode()),width/2,(int)(height*0.15),0xffffff);
        drawCenteredString(matrixStack,mc.font,EMCWorld.tc("tooltip.emcworld.lottery.cost",MathUtils.format(LotteryUtils.getBuyPrice(getLottery()))),(int)(width*0.83),(int)(height*0.5),0xffffff);
        drawString(matrixStack,mc.font,EMCWorld.tc("tooltip.emcworld.lottery.rate"),(int) (width * 0.25) - 30, (int) (height*0.77),0xffffff);
        this.text.render(matrixStack,mouseX,mouseY,partialTicks);
        for(Widget b : this.buttons){
            if(b instanceof ConfirmButton){
                b.active = canSend();
            }
            if(b instanceof UndoButton){
                b.active = canUndo();
            }
        }
        if(getScreenLotteryMode() != LotteryMode.TOW_BALL){
            List<Integer> temp = MathUtils.copyList(numList);
            drawString(matrixStack,font, EMCWorld.tc("tooltip.emcworld.lottery.num", LotteryItem.getString(temp)),(int)(width*0.25),(int)(height*base),0xffffff);
        }
        subRender(matrixStack);
    }

    protected void subRender(MatrixStack stack){

    }

    public List<Button> getNumButtons(){
        List<Button> list = new ArrayList<>();
        int c = 1;
        for (int i = 1; i <= 30; i++) {
            int x = 10 + 30 * c;
            int y = 75 + getLine(i) * 30;
            list.add(new NumButton(x,y,i));
            c++;
            if(c > 10){
                c = 1;
            }
        }
        return list;
    }

    private class ConfirmButton extends Button{
        public ConfirmButton() {
            super((int) (BuyLotteryScreen.this.width * 0.5), (int) (BuyLotteryScreen.this.height * 0.75),50,20,EMCWorld.tc("tooltip.emcworld.lottery.confirm"), Button::onPress);
        }

        @Override
        public void onPress(){
            if(canSend()){
                Minecraft.getInstance().setScreen(null);
                PacketHandler.sendToServer(new BuyLotteryPacket(toArray(numList),toArray(addList),getRate(),getScreenLotteryMode().getIndex()));
            }
        }
    }

    private class BackButton extends Button{

        public BackButton() {
            super((int) (BuyLotteryScreen.this.width * 0.65), (int) (BuyLotteryScreen.this.height * 0.75),50,20,EMCWorld.tc("tooltip.emcworld.lottery.back"), Button::onPress);
        }

        @Override
        public void onPress(){
            Minecraft.getInstance().setScreen(new ChooseLotteryScreen());
        }
    }

    private class UndoButton extends Button{
        public UndoButton(){
            super((int) (BuyLotteryScreen.this.width * 0.8), (int) (BuyLotteryScreen.this.height*0.75)-40,50,20,EMCWorld.tc("tooltip.emcworld.lottery.undo"),Button::onPress);
            active = false;
        }

        @Override
        public void onPress(){
            LotteryMode mode = getScreenLotteryMode();
            int index;
            if(mode == LotteryMode.TOW_BALL && lastIsShift && addList.size() > 0){
                index = addList.remove(addList.size()-1);
            }else{
                index = numList.remove(numList.size()-1);
            }
            for(Widget b : buttons){
                if(b instanceof NumButton){
                    NumButton button = (NumButton) b;
                    if(button.num == index){
                        button.active = true;
                    }
                }
            }
        }
    }

    private boolean canUndo(){
        return getScreenLotteryMode() == LotteryMode.TOW_BALL ? numList.size() + addList.size() >= 1 : numList.size() >= 1;
    }

    private String getMode(){
        LotteryMode mode = getScreenLotteryMode();
        String s = "";
        switch (mode){
            case SIMPLEX:
                s = "simple";
                break;
            case COMPOUND:
                s = "double";
                break;
            case TOW_BALL:
                s = "tb";
                break;
        }
        return s;
    }

    private class RefreshButton extends Button{

        public RefreshButton() {
            super((int) (BuyLotteryScreen.this.width * 0.8), (int) (BuyLotteryScreen.this.height*0.75),50,20,EMCWorld.tc("tooltip.emcworld.lottery.refresh"),Button::onPress);
        }

        @Override
        public void onPress(){
            numList.clear();
            addList.clear();
            buttons.forEach(b -> {
                if(!(b instanceof RefreshButton || b instanceof ConfirmButton || b instanceof UndoButton)){
                    b.active = true;
                }
            });
        }
    }

    private class NumButton extends Button{
        private final int num;

        public NumButton(int p_i232255_1_, int p_i232255_2_, int index) {
            super(p_i232255_1_, p_i232255_2_,20,20,EMCWorld.tc(index), Button::onPress);
            num = index;
        }

        @Override
        public void onPress(){
            LotteryMode mode = getScreenLotteryMode();
            lastIsShift = Screen.hasShiftDown();
            if(mode == LotteryMode.TOW_BALL && Screen.hasShiftDown()){
                addList.add(num);
            }else{
                numList.add(num);
            }
            this.active = false;
        }
    }

    public boolean canSend(){
        boolean flag = MathUtils.isNum(text.getValue());
        if(EMCHelper.getPlayerEMC(getMinecraft().player) < getLotteryPrice()){
            flag = false;
        }
        if(LotteryUtils.getLotteryRule(getLottery())){
            flag = false;
        }
        return flag;
    }

    private int getRate(){
        if(!MathUtils.isNum(text.getValue())){
            return 0;
        }else{
            return Integer.parseInt(text.getValue());
        }
    }

    private static int[] toArray(List<Integer> list){
        int[] a = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            a[i] = list.get(i);
        }
        return a;
    }

    protected Lottery getLottery(){
        return new Lottery(numList,addList,getRate(),getScreenLotteryMode());
    }

    private long getLotteryPrice(){
        return LotteryUtils.getBuyPrice(getLottery());
    }

    private static int getLine(int i){
        if(i <= 10){
            return 0;
        }
        if(i <= 20){
            return 1;
        }
        if(i <= 30){
            return 2;
        }
        return 3;
    }
}
