package terramain.sts.stsblocks;

import me.terramain.sts.StsSaveData;
import me.terramain.textexecuter.StaticTextEditor;
import me.terramain.textexecuter.TextIterator;

public class StsBlockCloseCode extends StsBlockText{
    private boolean roundBrackets;
    private boolean curlyBraces;
    private boolean squareBrackets;
    private boolean triangularBraces;
    private boolean quotation;
    private boolean doubleQuotation;

    private boolean skipQuotationBraces;
    private boolean strictEnd;

    private Character specialSymbol;


    public StsBlockCloseCode(StsSaveData saveData, String codeDescription) {
        super(saveData,codeDescription);
        readCodeDescription(codeDescription);
    }
    private void readCodeDescription(String codeDescription){
        TextIterator textIterator = new TextIterator(codeDescription);
        textIterator.previous();
        curlyBraces=false;
        squareBrackets=false;
        roundBrackets=false;
        doubleQuotation=false;
        quotation=false;
        triangularBraces=false;
        skipQuotationBraces=false;
        strictEnd=false;
        specialSymbol=null;

        while (textIterator.hasNext()){
            char c = textIterator.next();
            if (c=='0') curlyBraces=true;
            if (c=='1') squareBrackets=true;
            if (c=='2') roundBrackets=true;
            if (c=='3') doubleQuotation=true;
            if (c=='4') quotation=true;
            if (c=='5') triangularBraces=true;
            if (c=='s'){
                curlyBraces=true;
                squareBrackets=true;
                roundBrackets=true;
                doubleQuotation=true;
                quotation=true;
            }
            if (c=='S'){
                curlyBraces=true;
                squareBrackets=true;
                roundBrackets=true;
                doubleQuotation=true;
                quotation=true;
                triangularBraces=true;
            }
            if (c=='Q'){
                doubleQuotation=true;
                quotation=true;
            }
            if (c=='q') skipQuotationBraces=true;
            if (c=='F') strictEnd=true;
            if (c=='$') specialSymbol=textIterator.next();
        }
    }

    public boolean isRoundBrackets() {return roundBrackets;}
    public boolean isCurlyBraces() {return curlyBraces;}
    public boolean isSquareBrackets() {return squareBrackets;}
    public boolean isTriangularBraces() {return triangularBraces;}
    public boolean isQuotation() {return quotation;}
    public boolean isDoubleQuotation() {return doubleQuotation;}
    public boolean isSkipQuotationBraces() {return skipQuotationBraces;}
    public boolean isStrictEnd() {return strictEnd;}
    public Character getSpecialSymbol() {return specialSymbol;}

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("StsBlockCloseCode:\n");
        if (saveData!=null) stringBuilder.append(StaticTextEditor.addSpacesToLines(
                saveData.toString(),
                2
        ));
        stringBuilder.append("  codeDescription:").append(getText()).append('\n');
        return stringBuilder.toString();
    }
}
