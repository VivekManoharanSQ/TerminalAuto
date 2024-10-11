package com.sq.emulator;

import org.apache.commons.lang3.StringUtils;
import org.python.core.util.StringUtil;
import org.tn5250j.Session5250;
import org.tn5250j.SessionConfig;
import org.tn5250j.SessionPanel;
import org.tn5250j.TN5250jConstants;
import org.tn5250j.framework.tn5250.ScreenField;
import org.tn5250j.keyboard.KeyMnemonic;
import org.tn5250j.tools.LangTool;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DemoGitAS400 {

    public static void main(String[] args) throws IOException, InterruptedException {

        JFrame frame = new JFrame("AS400 5250 Emulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        Properties pros = new Properties();
        pros.put(TN5250jConstants.SESSION_TERM_NAME,"DemoVivek");
        pros.put(TN5250jConstants.SESSION_HOST,"pub400.com");
        pros.put(TN5250jConstants.SESSION_CONFIG_RESOURCE,"");
        pros.put(TN5250jConstants.SESSION_SCREEN_SIZE,"0");
        pros.put(TN5250jConstants.SESSION_TN_ENHANCED,"1");
        pros.put(TN5250jConstants.SESSION_HOST_PORT,"23");
        pros.put(TN5250jConstants.SESSION_TERM_NAME_SYSTEM,"1");
//        pros.load(new FileReader("D:\\Automation\\Intellij\\AS400_Automation\\EmulatorAutomation\\src\\main\\resources\\AS400.properties"));
        Session5250 session5250 = new Session5250(pros,"","DemoVivek",new SessionConfig("","DemoVivek"));
        session5250.connect();
        LangTool.init();
        SessionPanel panel = new SessionPanel(session5250);

        panel.setSession(session5250);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        panel.getScreen().getScreenFields().getCurrentField().getString();
        panel.getScreen().getScreenFields().getField(0).setString("VIVEKMANO");
        ScreenField f = panel.getScreen().getScreenFields().getField(0);
        System.out.println(f.getString());
        panel.getScreen().getScreenFields().getField(1).setString("Sqpub400@2705");
        Thread.sleep(2000);
        panel.getScreen().sendKeys(KeyMnemonic.ENTER);
        panel.getScreen().updateScreen();
        Thread.sleep(5000);
        char[] ch = panel.getScreen().getScreenAsChars();
        findfieldByText(ch,"==>");
        panel.getScreen().moveCursor(1522);
        panel.getScreen().sendKeys(KeyMnemonic.TAB);
        printScreen(ch);
//        panel.getScreen().getScreenFields().getField(0).setString("1");
        panel.getScreen().sendKeys("1");
        ch = panel.getScreen().getScreenAsChars();
        printScreen(ch);
        panel.getScreen().sendKeys(KeyMnemonic.ENTER);
        panel.getScreen().updateScreen();
        Thread.sleep(5000);
        ch = panel.getScreen().getScreenAsChars();
        printScreen(ch);
        session5250.disconnect();
    }

    private static void printScreen(char[] ch){
        StringBuilder sb = new StringBuilder();
        String sRepeated = IntStream.range(0, 80).mapToObj(i -> "-").collect(Collectors.joining(""));
        sb.append(sRepeated).append("\n|");
        for(int i=1; i<ch.length;i++){
            sb.append(ch[i-1]);
            if((i%80)==0){
                sb.append("|\n|");
            }
        }
        sb.append("|\n");
        sRepeated = IntStream.range(0, 80).mapToObj(i -> "-").collect(Collectors.joining(""));
        sb.append(sRepeated);
        System.out.println(sb);
    }

    private  static  void findfieldByText(char[] ch,String fieldlbl){
        System.out.println("Index of ==> " + fieldlbl + "is ==> " + StringUtils.indexOf(new String(ch),fieldlbl));

    }
}
