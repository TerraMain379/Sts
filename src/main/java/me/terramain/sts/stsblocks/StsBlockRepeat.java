package terramain.sts.stsblocks;

import me.terramain.sts.StsBlocks;
import me.terramain.sts.StsSaveData;
import me.terramain.textexecuter.StaticTextEditor;

public class StsBlockRepeat extends StsBlockStorage{
    private StsValueBlock loopCounterBlock;
    public StsBlockRepeat(StsSaveData saveData, StsBlocks stsBlocks, StsValueBlock loopCounterBlock) {
        super(saveData, stsBlocks);
        this.loopCounterBlock = loopCounterBlock;
    }
    public StsBlockRepeat(StsSaveData saveData, StsBlocks stsBlocks, StsBlock loopCounterBlock) {
        super(saveData, stsBlocks);
        if (loopCounterBlock instanceof StsValueBlock) {
            this.loopCounterBlock = (StsValueBlock) loopCounterBlock;
        }
        else this.loopCounterBlock = null;
    }
    public StsBlockRepeat(StsSaveData saveData, StsBlocks stsBlocks) {
        super(saveData, stsBlocks);
        this.loopCounterBlock = null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("StsBlockRepeat:\n");
        if (saveData!=null) stringBuilder.append(StaticTextEditor.addSpacesToLines(
                saveData.toString(),
                2
        ));
        if (loopCounterBlock!=null) {
            stringBuilder.append("  loopCounterBlock:\n");
            stringBuilder.append(StaticTextEditor.addSpacesToLines(
                    loopCounterBlock.toString(),
                    4
            ));
        }
        stringBuilder.append(StaticTextEditor.addSpacesToLines(
                stsBlocks.toString(),
                2
        ));
        return stringBuilder.toString();
    }

    public StsValueBlock getLoopCounterBlock() {
        return loopCounterBlock;
    }
}
