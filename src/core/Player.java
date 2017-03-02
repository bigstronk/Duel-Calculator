package core;

import java.text.NumberFormat;

/**
 * Created by Phil on 2/25/2017.
 */
public class Player {
    public String name;
    public int att, str, def, maxHp, hp;
    public int maxHit, maxAttRoll, maxDefRoll;
    AttackStyle attackStyle;
    Weapon weapon;
    public int attacks = 0; //use for dds specs, could create an attack tick value for a more advanced version
    public int wins = 0;
    public int totalDuels = 0;
    public int spec; //use for dds
    public int avgHp;
    public int pidTotal = 0;
    public int pidWin = 0;
    public boolean pid = false;
    public double accuracy;

    public Player(){}
    public Player(String name, int att, int str, int def, int hp, int maxHp) {
        this.name = name;
        this.att = att;
        this.str = str;
        this.def = def;
        this.hp = hp;
        this.maxHp = maxHp;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAtt(int att) {
        this.att = att;
    }

    public int getAtt() {
        return att;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public int getStr() {
        return str;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getDef() {
        return def;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHit(int maxHit){
        this.maxHit = maxHit;
    }

    public int getMaxHit(){
        return maxHit;
    }

    public void setAttackStyle(AttackStyle attackStyle){
        this.attackStyle = attackStyle;
    }

    public AttackStyle getAttackStyle(){
        return attackStyle;
    }

    public void setWeapon(Weapon weapon){
        this.weapon = weapon;
    }

    public Weapon getWeapon(){
        return weapon;
    }

    public void setMaxAttRoll(int maxAttRoll){
        this.maxAttRoll = maxAttRoll;
    }

    public int getMaxAttRoll(){
        return maxAttRoll;
    }

    public void setMaxDefRoll(int maxDefRoll){
        this.maxDefRoll = maxDefRoll;
    }

    public int getMaxDefRoll(){
        return maxDefRoll;
    }

    public void setWins(int wins){
        this.wins = wins;
    }

    public int getWins(){
        return wins;
    }

    public void setAvgHp(int avgHp){
        this.avgHp = avgHp;
    }

    public int getAvgHp(){
        return avgHp;
    }

    public void setPidTotal(int pidTotal){
        this.pidTotal = pidTotal;
    }

    public int getPidTotal(){
        return pidTotal;
    }

    public void setPidWin(int pidWin){
        this.pidWin = pidWin;
    }

    public int getPidWin(){
        return pidWin;
    }

    public void setPid(boolean pid){
        this.pid = pid;
    }

    public boolean getPid(){
        return pid;
    }

    public void setTotalDuels(int totalDuels){
        this.totalDuels = totalDuels;
    }

    public int getTotalDuels(){
        return totalDuels;
    }

    public void reset() {
        hp = maxHp;
        pid = false;
        attacks = 0;
        spec = 100;
    }

    public void setAccuracy(double accuracy){
        this.accuracy = accuracy;
    }

    public double getAccuracy(){
        return accuracy;
    }


    public String toString() {
        NumberFormat f = NumberFormat.getPercentInstance();

        f.setMinimumFractionDigits(1);
        f.setMaximumFractionDigits(2);
        f.setMinimumIntegerDigits(1);
        f.setMaximumIntegerDigits(3);

        return ((Program.toCaps(getName()) + ": "
                + f.format((double)getWins() / getTotalDuels()) + "\n(w/ "
                + f.format((double)getPidWin() / getPidTotal()) + " & w/o "
                + f.format((double)(getWins() - getPidWin()) / (getTotalDuels() - getPidTotal())) + ")" + "\n"));
    }

}







