
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {


        try {
            System.out.println(licz(Opn(readData())));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String readData(){
        BufferedReader data = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Wpisz operacje do wykonania:");
            return data.readLine();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }


    private static String Opn(String stringIN) throws Exception {
        StringBuilder stringOperator = new StringBuilder(""), stringOut = new StringBuilder("");
        char cIn, cTemp;//

        for (int i = 0; i < stringIN.length(); i++) {
            cIn = stringIN.charAt(i);
            if (ifOperator(cIn)) {
                while (stringOperator.length() > 0) {//jeśli wiecej niz jeden operator


                    cTemp =stringOperator.substring(stringOperator.length()-1).charAt(0);// ctmp - znak o najwyzszym prio z ciagu sbstack
                    if (ifOperator(cTemp) && (Prio(cIn) <= Prio(cTemp))) {//jesli nowy operator jest tak samo lub mniej wazny niz stary
                        stringOut.append(" ").append(cTemp).append(" ");//dodaj stary do wyjscia
                        stringOperator.setLength(stringOperator.length()-1);//usun stary operator z stringa tymczasowego
                    } else {
                        stringOut.append(" ");//dodaj spacje jesli prio jest wieksze
                        break;
                    }
                }
                stringOut.append(" ");//dodaj spacje
                stringOperator.append(cIn);//dodaj znak operatora na koniec tymczasowego stringa znakow
            }  else {

                stringOut.append(cIn);// Dodaje znak na koniec stringOut
            }
        }


        while (stringOperator.length() > 0) {
            stringOut.append(" ").append(stringOperator.substring(stringOperator.length()-1));//dodaje znaki od konca z tymczasowego stringa do glownego
            stringOperator.setLength(stringOperator.length()-1);
        }

        return  stringOut.toString();
    }


    private static boolean ifOperator(char c_op) {
        switch (c_op) {
            case '-':
            case '+':
            case '*':
            case '/':
            case '^':
            case '%':
                return true;
        }
        return false;
    }




    private static double licz(String stringIN) throws Exception {
        double l1 = 0, l2 = 0;
        String stringTemp;
        Deque<Double> stack = new ArrayDeque<Double>();//utworzenie stosu liczb
        StringTokenizer st = new StringTokenizer(stringIN);// podzial stringa na częsci
        while(st.hasMoreTokens()) {//dla wszystkich czesci stringa
            try {
                stringTemp = st.nextToken().trim();
                if (1 == stringTemp.length() && ifOperator(stringTemp.charAt(0))) {
                    if (stack.size() < 2) {
                        throw new Exception("blad w stosie" + stringTemp);
                    }
                    l2 = stack.pop();
                    l1 = stack.pop();
                    switch (stringTemp.charAt(0)) {
                        case '+':
                            l1 += l2;
                            break;
                        case '-':
                            l1 -= l2;
                            break;
                        case '/':
                            if(l2 == 0) {
                                throw new Exception("nie dziel przez 0" + stringTemp);
                            }
                            l1 /= l2;
                            break;
                        case '*':
                            l1 *= l2;
                            break;
                        case '%':
                            l1 %=l2;
                            break;
                        case '^':
                            l1 = Math.pow(l1, l2);
                            break;
                        default:
                            throw new Exception("nieznana operacja" + stringTemp);
                    }
                    stack.push(l1);
                } else {
                    l1 = Double.parseDouble(stringTemp);
                    stack.push(l1);
                }
            } catch (Exception e) {
                throw new Exception("nieprawidlowy znak");
            }
        }

        if (stack.size() > 1) {
            throw new Exception("za duzo operatow");
        }

        return stack.pop();
    }







    private static byte Prio(char operator) {
        switch (operator) {
            case '^':
                return 3;
            case '*':
            case '/':
            case '%':
                return 2;
        }
        return 1;
    }
}
