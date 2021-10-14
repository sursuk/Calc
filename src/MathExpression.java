import java.util.Scanner;

public class MathExpression {
    private int num0 = 0, num1 = 0;
    private int result = 200;
    private int operator = 0;
    private boolean ss = false; //  if 0 - arabian, else - rome;

    MathExpression(){
        for(int counter = 0; input(); counter++){
            if(counter >= 2){
                System.out.println("Если хотите выйти из программы введите Y, иначе введите любой другой символ");
                Scanner in = new Scanner(System.in);
                if(in.nextLine().equals("Y")) return;
            }
        }
        switch (operator) {
            case 0: this.result = num0 + num1; break;
            case 1: this.result = num0 - num1; break;
            case 2: this.result = num0 * num1; break;
            case 3: this.result = num0 / num1; break;
            default: break;
        }
    }

    @Override
    public String toString() {
        return "MathExpression{" +
                "num0=" + num0 +
                ", num1=" + num1 +
                ", result=" + result +
                ", operator=" + operator +
                ", ss=" + ss +
                '}';
    }

    boolean input(){
        Scanner input = new Scanner(System.in);
        //здесь определение сс
        String tempInputStr = input.nextLine();
        if(tempInputStr.indexOf(' ') == -1
           && tempInputStr.lastIndexOf(' ') == -1
           || tempInputStr.indexOf(' ') == tempInputStr.lastIndexOf(' ')){

            System.out.println("Пожалуйства введите пример, разделяя операнды и операнд пробелами\n-------------------");
            return true;
        }

        try {
            num0 = Integer.parseInt(tempInputStr.substring(0, tempInputStr.indexOf(' ')));
            ss = false;
        }catch (NumberFormatException exception){
            num0 = toArab(tempInputStr.substring(0, tempInputStr.indexOf(" ")));
            ss = true;
        }

        switch (tempInputStr.substring(tempInputStr.indexOf(' ')+1, tempInputStr.lastIndexOf(" "))){
            case"+": operator = 0; break;
            case"-": operator = 1; break;
            case"*": operator = 2; break;
            case"/": operator = 3; break;
            default: throw new Exception("Ошибка0 - неверно определен оператор");
        }
        if(ss){
            try{
                num1 = toArab(tempInputStr.substring(tempInputStr.lastIndexOf(" ")+1));
            }catch (IllegalStateException exception){
                throw new Exception("Система счисления не соответсвует");
            }
        }else{
            try {
                num1 = Integer.parseInt(tempInputStr.substring(tempInputStr.lastIndexOf(" ")+1));
            } catch (NumberFormatException exception) {
                throw new Exception("Система счисления не соответсвует");
            }
        }
        return false;
    }

    String toRoman(int arabNumber){

        if(arabNumber < 0) throw new Exception("Римские цифры не могут быть отрицательными");

        enum Numeral {
            I(1),
            IV(4),
            V(5),
            IX(9),
            X(10),
            XL(40),
            L(50),
            XC(90),
            C(100);

            int weight;

            Numeral(int weight) {
                this.weight = weight;
            }
        }
        StringBuilder buf = new StringBuilder();
        final Numeral[] values = Numeral.values();
        for (int i = values.length - 1; i >= 0; i--) {
            while (arabNumber >= values[i].weight) {
                buf.append(values[i]);
                arabNumber -= values[i].weight;
            }
        }
        return buf.toString();
    }
    int toArab(String romanNumber){
        int resultNumber = 0;
        for(int i = 0; i < romanNumber.length(); i++){
            switch (romanNumber.charAt(i)){
                case'I': resultNumber+= 1; break;
                case'V': resultNumber+= 5; break;
                case'X': resultNumber+= 10; break;
                case'L': resultNumber+= 50; break;
                case'C': resultNumber+= 100; break;
                default:
                    throw new IllegalStateException("Unexpected value: " + romanNumber.charAt(i));
            }
        }
        return resultNumber;
    }

    String getResult() {
        if(result == 200){
            return "";
        }
        if (ss) {//rome
            return toRoman(result);
        } else {//arabian
            return String.valueOf(result);
        }
    }
}
