import java.util.Scanner;
/*
Написать игру Миллионер через классы ООП

*Дописать админскую часть
CRUD (CREATE READ UPDATE DELETE ) Вопросы, Статистику игры

TODO задание со звездочкой, к сожалению, не выполнено: не смог выкроить время ((
 */

class Questions {
    private String[] question = {
            "Кого рыбак всегда видит издалека?",
            "Вокруг чего обводят одураченного субъекта?",
            "Какое из этих женских имен в переводе с латинского означает 'Победа'?",
            "Чему равен периметр ромба со стороной 2 м?",
            "Так называются дополнения к основному сюжету игры?"
    };

    public String[] getQuestion() {
        return question;
    }
}

class Answers {
    private String[][] answers = {
            {"A: Рыбака", "B: Рыбовода", "C: Рыбнадзор", "D: Червяка"},
            {"A: Пальца", "B: Здания мэрии", "C: Собственной оси", "D: Поля чудес"},
            {"A: Виктория", "B: Олимпиада", "C: Ноябрина", "D: Капитолина"},
            {"A: 8 метров", "B: 4 метра", "C: 6 литров", "D: 8 метров квадратных"},
            {"A: Аддон", "B: Сториап", "C: Апгрейд", "D: Скилл"}
    };

    public String[][] getAnswers() {
        return answers;
    }
}

public class Main {

    public static boolean[] help = {true, true, true};  // массив подсказок: "1: 50 на 50.", "2: Звонок другу.", "3: Помощь зала."
    public static int[] bet = {20000, 80000, 150000, 250000, 500000};  // стоимость вопросов
    public static int currentSum = 0;  // сумма выигрыша
    public static boolean game = true;  // играем, пока true
    public static int turnNumber = 0;  // счетчик хода
    public static String answer = "";  // рандомный ответ друга или зала
    public static Questions gameQuestions = new Questions();  // TODO создаем объект вопросов и присваиваем ему модификаторы доступа public static для видимости в методах класса Main
    public static Answers gameAnswers = new Answers();  // TODO создаем объект ответов и присваиваем ему модификаторы доступа public static для видимости в методах класса Main



    ////// Задаем вопрос
    public static void turnDialog() {
        System.out.println("==========================================================");
        System.out.printf("Вопрос №%d (стоимостью %d денег): ", turnNumber + 1, bet[turnNumber]);
        System.out.println(gameQuestions.getQuestion()[turnNumber]);    //question[turnNumber]);
        menu();
    }

    ////// Проверяем наличие подсказок
    public static boolean checkHelp() {
        for (int i = 0; i < 3; i++) {
            if (help[i]) {
                return true;
            }
        }
        return false;
    }

    ////// меню возможных ответов
    public static void menu() {
        System.out.print("Выберите ответ (введите соответствующую букву): ");
        for (int i = 0; i < gameAnswers.getAnswers()[turnNumber].length; i++) {
            System.out.print(gameAnswers.getAnswers()[turnNumber][i]);
            if (gameAnswers.getAnswers()[turnNumber][i] != "") System.out.print("   ");
        }
        if (checkHelp()) {  // меню с подсказками отображаем, если есть неиспользованная подсказка
            System.out.println("");
            System.out.print("Можно воспользоваться подсказкой (ввести соответствующую цифру): ");
            if (help[0]) System.out.print("1: 50 на 50   ");
            if (help[1]) System.out.print("2: Звонок другу   ");
            if (help[2]) System.out.print("3: Помощь зала");
        }
        System.out.println("");
        System.out.print("Ваш выбор: ");
        userTurn();
    }

    ////// Ввод пользователем ответа/подсказки
    public static void userTurn() {
        Scanner scanner = new Scanner(System.in);
        boolean work = true;
        while (work) {
            if (scanner.hasNextInt()) {
                int temp = scanner.nextInt();
                if (temp == 1 && help[0] == true) {
                    fifty();  // уходим в метод, реализующий подсказку "50 на 50", и возвращаемся
                    work = false;
                } else if (temp == 2 && help[1] == true) {
                    callFriend();  // уходим в метод "Звонок другу", и возвращаемся
                    work = false;
                } else if (temp == 3 && help[2] == true) {
                    helpViewers();  // уходим в метод "Помощь зала", и возвращаемся
                    work = false;
                } else System.out.println("Будьте внимательны при вводе!");
            } else if (scanner.hasNextLine()) {
                String temp = scanner.nextLine();
                if (temp.equalsIgnoreCase("A")) {
                    currentSum += bet[turnNumber];
                    System.out.printf("Поздравляем! Ответ верный! Ваша выигрышная сумма составляет %d денег!", currentSum);
                    System.out.println("");
                    turnNumber++;
                    work = false;
                } else if ((temp.equalsIgnoreCase("B") && gameAnswers.getAnswers()[turnNumber][1] != "") ||
                        (temp.equalsIgnoreCase("C") && gameAnswers.getAnswers()[turnNumber][2] != "") ||
                        (temp.equalsIgnoreCase("D") && gameAnswers.getAnswers()[turnNumber][3] != "")) {
                    System.out.printf("К сожалению, ответ не верный! Игра завершена! Ваша выигрышная сумма составляет %d денег!", currentSum);
                    game = false;
                    work = false;
                } else System.out.println("Будьте внимательны при вводе!");
            }
        }
    }

    ////// Метод "50 на 50"
    public static void fifty() {
        help[0] = false;
        int rand = (int) ((Math.random() * 3) + 1);
        for (int i = 1; i < 4; i++) {
            if (i != rand){
                gameAnswers.getAnswers()[turnNumber][i] = "";  // в массиве ответов затираем ответы в диапазоне индексов 1-3, кроме выбранного рандомно
            }
        }
        menu();
    }

    ////// Метод "Звонок другу"
    public static void callFriend() {
        help[1] = false;  // выключаем подсказку из отображаемых
        System.out.println("Звонок другу: ");
        spin();
        random();
        System.out.println("Друг посоветовал вариант " + answer);
        menu();
    }

    ////// Метод "Помощь зала"
    public static void helpViewers() {
        help[2] = false;  // выключаем подсказку из отображаемых
        System.out.println("Зрители голосуют: ");
        spin();
        random();
        System.out.println("Зрители советуют вариант " + answer);
        menu();
    }

    ////// Просто анимация вращения символа (когда узнал о существовании \b и решил где-то его использовать :) )
    public static void spin() {
        char[] callSymbols = {'|', '/', '-', '\\'};
        for (int i = 0; i < 3; i++) {
            for (char callSymbol : callSymbols) {
                System.out.print(callSymbol);
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.print("\b");  //
            }
        }
    }

    ////// Рандомизатор выбора ответа
    public static void random() {
        // вычисляем количество отображаемых ответов:
        int countUseful = 0;
        for (int i = 0; i < 4; i++) {
            if (gameAnswers.getAnswers()[turnNumber][i] != "")
                countUseful++;
        }
        // записываем индексы отображаемых ответов в служебный массив
        int[] tempIndex = new int[countUseful];
        for (int i = 0, j = 0; i < 4; i++) {
            if (gameAnswers.getAnswers()[turnNumber][i] != "") {
                tempIndex[j] = i;
                j++;
            }
        }
        // рандомизируем вариант ответа
        int decision = (int) ((Math.random() * 10));
        if (decision < 7) answer = "A";  // 70% успешного ответа
        else if (tempIndex.length == 2 && decision >= 7)
            answer = gameAnswers.getAnswers()[turnNumber][tempIndex[1]];  // при двух возможных ответах неправильный ответ 30%
        else if (tempIndex.length == 4 && decision == 7)
            answer = gameAnswers.getAnswers()[turnNumber][tempIndex[1]];  // при четырех возможных ответах ответ B = 10%
        else if (tempIndex.length == 4 && decision == 8)
            answer = gameAnswers.getAnswers()[turnNumber][tempIndex[2]];  // при четырех возможных ответах ответ C = 10%
        else answer = gameAnswers.getAnswers()[turnNumber][tempIndex[3]];  // при четырех возможных ответах ответ D = 10%
    }


    public static void main(String[] args) {

        System.out.println("Отладочная информация: правильный ответ всегда А\n");
        System.out.println("-= Вас приветствует игра \"Кто хочет стать миллионером\"! =-");
        while (game) {
            turnDialog();
            if (turnNumber == gameQuestions.getQuestion().length) {  // когда достигаем конца списка вопросов
                game = false;
                System.out.println("ВЫ СТАЛИ МИЛЛИОНЕРОМ!!! Игра завершена.");
            }
        }
    }
}
