package terramain.sts;

import me.terramain.sts.stsblocks.StsBlock;
import me.terramain.textexecuter.StaticTextEditor;

import java.util.ArrayList;
import java.util.List;

public class StsBlocks extends ArrayList<StsBlock> {
    public StsBlocks(List<StsBlock> stsBlocks) {
        this();
        this.addAll(stsBlocks);
    }
    public StsBlocks() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("stsBlocks:\n");
        for (StsBlock stsBlock:this){
            stringBuilder.append(StaticTextEditor.addSpacesToLines(
                    stsBlock.toString(),
                    2
            ));
        }
        return stringBuilder.toString();
    }
}
