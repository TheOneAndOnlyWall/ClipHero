package infrastructure;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import gui.MainGui;

public class JavaMain {

    public static void main(String[] args) {
        FlatOneDarkIJTheme.setup();

        MainGui testGui =new MainGui();
    }

}
