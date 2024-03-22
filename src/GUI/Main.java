package GUI;

public class Main {
    private LoginGUI loginGUI;
    public static void  main(String[] args){

        Main main = new Main();
        main.initialize();
    }

    private void initialize() {
        loginGUI = new LoginGUI(); // Instantiate LoginGUI
    }


}
