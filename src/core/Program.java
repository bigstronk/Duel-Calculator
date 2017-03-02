package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Phil on 2/25/2017.
 */
public class Program {
    private static boolean hs = true;

    public static void searchHiscores(Player p) throws IOException {
        String emptyURL = "http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player=";
        URL playerURL = new URL(emptyURL.concat(p.getName().replaceAll(" ", "+")));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(playerURL.openStream()));

        String inputLine;
        int[] stats = new int[4];
        for (int i = 0; i < 5; i++) {
            inputLine = in.readLine();
            if (i >= 1 && i < 5) {
                String[] values = inputLine.split(",");
                stats[i - 1] = Integer.parseInt(values[1]);
            }
        }

        p.setAtt(stats[0]);
        p.setDef(stats[1]);
        p.setStr(stats[2]);
        p.setHp(stats[3]);
        p.setMaxHp(stats[3]);

    }

    public static void calcMaxHit(Player p) {
        int weaponStrBonus = p.getWeapon().getStrBonus();
        double multiplier = p.getWeapon().getMultiplier();

        int effStr = p.getStr() + 8 + p.getAttackStyle().getStrengthBonus();
        p.setMaxHit((int) ((int) (Math.floor(5 + effStr + effStr * weaponStrBonus / 64.0) / 10.0) * multiplier));
    }

    public static void calcRolls(Player p) {
        int effAtk = p.getAtt() + p.getAttackStyle().getAttackBonus() + 8;
        int effDef = p.getDef() + p.getAttackStyle().getDefenceBonus() + 8;
        int styleBonus = 0;

        if (p.getWeapon() == Weapon.Tentacle) {
            styleBonus = Weapon.Tentacle.getSlashBonus();
        } else if (p.getWeapon() == Weapon.DScim) {
            styleBonus = Weapon.DScim.getSlashBonus();
        } else if (p.getWeapon() == Weapon.DDS) {
            styleBonus = Weapon.DDS.getStabBonus();
        }

        if (p.getWeapon() != Weapon.DScim) {
            p.setMaxDefRoll((effDef) * (64));
        } else {
            p.setMaxDefRoll((effDef) * (64 + 1));
        }

        p.setMaxAttRoll((effAtk) * (styleBonus + 64));

    }

    public static void calcAccuracy(Player p1, Player p2){
        if(p1.getMaxAttRoll() < p2.getMaxDefRoll()){
            p1.setAccuracy(0.5 * p1.getMaxAttRoll() / p2.getMaxDefRoll());
        }else if (p1.getMaxAttRoll() > p2.getMaxDefRoll()) {
            p1.setAccuracy(1 - 0.5 * ((double) p2.getMaxDefRoll() / (double)p1.getMaxAttRoll()));
        }

        if(p2.getMaxAttRoll() < p1.getMaxDefRoll()){
            p2.setAccuracy(0.5 * p2.getMaxAttRoll() / p1.getMaxDefRoll());
        }else if (p2.getMaxAttRoll() > p1.getMaxDefRoll()) {
            p2.setAccuracy(1 - 0.5 * ((double)p1.getMaxDefRoll() / (double)p2.getMaxAttRoll()));
        }

    }

    public static void duel(Player p1, Player p2) {
        Random r = new Random();

        while (p1.getHp() > 0 && p2.getHp() > 0) {  //While both are still alive

            if (p1.getHp() > 0) { //if p1 has hp > 0 (ie; still alive)
                if (p1.getAccuracy() > r.nextDouble()) { //roll random double and see if p1 is accurate
                    p2.setHp(p2.getHp() - (r.nextInt(p1.getMaxHit() + 1))); //set p2's hp to his current hp, minus a random hit from 0 to p1's max.
                }
            }

            if (p2.getHp() > 0) { //if p2 has hp > 0 (ie; still alive)
                if ((p2.getAccuracy()) > r.nextDouble()) { //roll random double and see if p2 is accurate
                    p1.setHp(p1.getHp() - (r.nextInt(p2.getMaxHit() + 1))); //set p1's hp to his current hp, minus a random hit from 0 to p2's max.
                }
            }

            if (p1.getHp() > 0 && p2.getHp() <= 0) { //if p1 is alive and p2 is dead give p1 a win, and a pid win
                p1.setWins(p1.getWins() + 1);
                if (p1.getPid()) {
                    p1.setPidWin(p1.getPidWin() + 1);
                }

            } else if (p1.getHp() <= 0 && p2.getHp() > 0) { //if p1 is dead and p2 is alive give p2 a win, and a pid win
                p2.setWins(p2.getWins() + 1);
                if (p2.getPid()) {
                    p2.setPidWin(p2.getPidWin() + 1);
                }
            }

        }

        p1.setTotalDuels(p1.getTotalDuels() + 1);
        p2.setTotalDuels(p2.getTotalDuels() + 1);

        p1.reset();
        p2.reset();

    }

    public static String toCaps(String s){
        StringBuffer res = new StringBuffer();

        String[] strArr = s.split(" ");
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);

            res.append(str).append(" ");
        }

        return res.toString().trim();
    }

}





