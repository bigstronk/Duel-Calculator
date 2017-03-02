package core;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class DuelController implements Initializable {

    @FXML //fx:id="p1"
    public TextField p1;
    @FXML //fx:id="p2"
    public TextField p2;
    @FXML //fx:id="textArea"
    public TextArea textArea;

    @FXML //fx:id="p1Style"
    public MenuButton p1Style;
    @FXML //fx:id="p1Accurate"
    public MenuItem p1Accurate;
    @FXML //fx:id="p1Aggressive"
    public MenuItem p1Aggressive;
    @FXML //fx:id="p1Controlled"
    public MenuItem p1Controlled;
    @FXML //fx:id="p1Defensive"
    public MenuItem p1Defensive;
    @FXML //fx:id="p1RotateAD"
    public MenuItem p1RotateAD;
    @FXML //fx:id="p1RotateCD"
    public MenuItem p1RotateCD;

    @FXML //fx:id="p2Style"
    public MenuButton p2Style;
    @FXML //fx:id="p2Accurate"
    public MenuItem p2Accurate;
    @FXML //fx:id="p2Aggressive"
    public MenuItem p2Aggressive;
    @FXML //fx:id="p2Controlled"
    public MenuItem p2Controlled;
    @FXML //fx:id="p2Defensive"
    public MenuItem p2Defensive;
    @FXML //fx:id="p2RotateAD"
    public MenuItem p2RotateAD;
    @FXML //fx:id="p2RotateCD"
    public MenuItem p2RotateCD;


    @FXML //fx:id="duelType"
    public MenuButton duelType;
    @FXML //fx:id="tentSpecOff"
    public MenuItem tentSpecOff;
    @FXML //fx:id="tentSpecOn"
    public MenuItem tentSpecOn;
    @FXML //fx:id="dScimSpecOff"
    public MenuItem dScimSpecOff;
    @FXML //fx:id="dScimSpecOn"
    public MenuItem dScimSpecOn;
    @FXML //fx:id="noWeapon"
    public MenuItem noWeapon;

    @FXML //fx:id="runButton"
    public Button runButton;
    @FXML //fx:id="clearButton"
    public Button clearButton;
    @FXML //fx:id="exitButton"
    public Button exitButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Player 1 Selections//
        p1Accurate.setOnAction(event -> updateMenu(p1Style, p1Accurate));
        p1Aggressive.setOnAction(event -> updateMenu(p1Style, p1Aggressive));
        p1Controlled.setOnAction(event -> updateMenu(p1Style, p1Controlled));
        p1Defensive.setOnAction(event -> updateMenu(p1Style, p1Defensive));
        p1RotateAD.setOnAction(event -> updateMenu(p1Style, p1RotateAD));
        p1RotateCD.setOnAction(event -> updateMenu(p1Style, p1RotateCD));

        //Player 2 Selections//
        p2Accurate.setOnAction(event -> updateMenu(p2Style, p2Accurate));
        p2Aggressive.setOnAction(event -> updateMenu(p2Style, p2Aggressive));
        p2Controlled.setOnAction(event -> updateMenu(p2Style, p2Controlled));
        p2Defensive.setOnAction(event -> updateMenu(p2Style, p2Defensive));
        p2RotateAD.setOnAction(event -> updateMenu(p2Style, p2RotateAD));
        p2RotateCD.setOnAction(event -> updateMenu(p2Style, p2RotateCD));


        //Duel Type Selections
        tentSpecOff.setOnAction(event -> updateMenu(duelType, tentSpecOff));
        tentSpecOn.setOnAction(event -> updateMenu(duelType, tentSpecOn));
        dScimSpecOff.setOnAction(event -> updateMenu(duelType, dScimSpecOff));
        dScimSpecOn.setOnAction(event -> updateMenu(duelType, dScimSpecOn));
        noWeapon.setOnAction(event -> updateMenu(duelType, noWeapon));

        //See run()
        runButton.setOnAction(event -> {
            try {
                run();
            } catch (IOException e) {
                textArea.clear();
                updateText("Error Retrieving Hiscores...");
            }
        });

        //See reset()
        clearButton.setOnAction(event -> reset());

        //See exit()
        exitButton.setOnAction(event -> System.exit(0));

    }

    private void run() throws IOException {
        Player player1 = new Player();
        Player player2 = new Player();

        Map p1Map = newItemMap(p1Style);
        Map p2Map = newItemMap(p2Style);
        Map dtMap = newItemMap(duelType);

        //Iterates through all MenuItems (player 1) and returns key value of the selected MenuItem
        p1Style.getItems().stream().filter(item -> Objects.equals(p1Style.getId(), item.getId())).forEach(item -> retrieveStyle(p1Map, item, player1));

        //Iterates through all MenuItems (player 2) and returns key value of the selected MenuItem
        p2Style.getItems().stream().filter(item -> Objects.equals(p2Style.getId(), item.getId())).forEach(item -> retrieveStyle(p2Map, item, player2));

        //Iterates through all MenuItems (Duel Type) and returns key value of the selected MenuItem
        duelType.getItems().stream().filter(item -> Objects.equals(duelType.getId(), item.getId())).forEach(item -> retrieveDuelType(dtMap, item, player1, player2));

        if (checkValues()) {

            //Set players names
            player1.setName(p1.getText());
            player2.setName(p2.getText());
            //Search players hiscores
            Program.searchHiscores(player1);
            Program.searchHiscores(player2);
            //Calculate Max Hits
            Program.calcMaxHit(player1);
            Program.calcMaxHit(player2);
            //Calculate Max Rolls
            Program.calcRolls(player1);
            Program.calcRolls(player2);
            //Calculate Accuracy
            Program.calcAccuracy(player1,player2);

            for (int i = 0; i < 1000000; i++) {
                if (i % 2 == 0) {
                    Program.duel(player1, player2);
                    player1.setPid(false);
                    player2.setPid(true);
                    player1.setPidTotal(player1.getPidTotal() + 1);
                } else if (i % 2 == 1) {
                    Program.duel(player2, player1);
                    player2.setPid(false);
                    player1.setPid(true);
                    player2.setPidTotal(player2.getPidTotal() + 1);
                }
            }

            //Post player information to TextArea
            updateText(player1.toString() + "\n" + player2.toString());
        } else if(!checkValues()){ //Post Error to TextArea
            reset();
            updateText("(ERROR) Make sure all field's are completed before running! \n(Note) TENTACLE CANNOT be set to AGGRESSIVE!");
        }

    }

    /* Takes the MenuButton parameter accepted by the method and adds each MenuItem value to a hashmap, and stores
     * each value with an incrementing key. Useful for passing values around. */
    private Map<MenuItem, Integer> newItemMap(MenuButton m) {
        Map<MenuItem, Integer> selections = new HashMap<>();
        int k = 0;
        for (MenuItem i : m.getItems()) {
            selections.put(i, k + 1);
            k++;
        }

        return selections; //MenuButton's list of MenuItems (descending) with ascending key values. ie; top item = 1
    }

    private void retrieveStyle(Map m, MenuItem i, Player p) {
        int key = (int) m.get(i);

        if (key == 1) {
            p.setAttackStyle(AttackStyle.Accurate);
        } else if (key == 2) {
            p.setAttackStyle(AttackStyle.Aggressive);
        } else if (key == 3) {
            p.setAttackStyle(AttackStyle.Controlled);
        } else if (key == 4) {
            p.setAttackStyle(AttackStyle.Defensive);
        } else if (key == 5) {
            p.setAttackStyle(AttackStyle.RotatingA);
        } else if (key == 6) {
            p.setAttackStyle(AttackStyle.RotatingC);
        }

    }

    private void retrieveDuelType(Map m, MenuItem i, Player p1, Player p2) {
        int key = (int) m.get(i);

        if (key == 1) {
            p1.setWeapon(Weapon.Tentacle);
            p2.setWeapon(Weapon.Tentacle);
        } else if (key == 3) {
            p1.setWeapon(Weapon.DScim);
            p2.setWeapon(Weapon.DScim);
        } else if (key == 2 || key == 4) {
            p1.setWeapon(Weapon.DDS);
            p2.setWeapon(Weapon.DDS);
        } else if (key == 5) {
            p1.setWeapon(Weapon.NONE);
            p2.setWeapon(Weapon.NONE);
        }

    }

    private boolean checkValues() {
        return !Objects.equals(duelType.getId(), tentSpecOff.getId()) || !Objects.equals(p1Style.getId(), p1Aggressive.getId()) && !Objects.equals(p2Style.getId(), p2Aggressive.getId());
    }

    private void updateMenu(MenuButton b, MenuItem m) {
        b.setId(m.getId());
        b.setText(m.getText());
    }

    public void updateText(String s) {
        textArea.clear();
        textArea.setText(s);
    }

    private void reset() {
        p1.clear();
        p2.clear();
        p1.setPromptText("Your Username");
        p2.setPromptText("Opponent");
        p1Style.setText("Attack Style");
        p2Style.setText("Attack Style");
        duelType.setText("Duel Type");
        textArea.clear();
    }

}
















