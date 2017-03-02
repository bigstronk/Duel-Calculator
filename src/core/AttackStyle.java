package core;

/**
 * Created by Phil on 2/25/2017.
 */
public enum AttackStyle {
    Accurate("Accurate",3,0,0),
    Aggressive("Aggressive",0,3,0),
    Controlled("Controlled",1,1,1),
    Defensive("Defensive",0,0,3),
    RotatingA("RotatingA",3,0,3),
    RotatingC("RotatingC",1,1,3);

    AttackStyle(String style, int attBonus, int strBonus, int defBonus) {
        this.style = style;
        this.attBonus = attBonus;
        this.strBonus = strBonus;
        this.defBonus = defBonus;
    }

    private final String style;
    private final int attBonus;
    private final int strBonus;
    private final int defBonus;

    public String getStyle(){
        return style;
    }

    public int getAttackBonus() {
        return attBonus;
    }

    public int getStrengthBonus() {
        return strBonus;
    }

    public int getDefenceBonus() {
        return defBonus;
    }
}
