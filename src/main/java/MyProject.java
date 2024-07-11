import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MyProject {
    public static void main(String[] args) throws IOException, ParseException {
        //createJsonList();
        Scanner scanner = new Scanner(System.in);



        System.out.print("Enter username:");
        String name = scanner.nextLine();

        System.out.print("Enter password:");
        String pass = scanner.nextLine();

        loginSystem(name,pass);
    }

    public static void loginSystem(String name,String pass) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonUserArray = (JSONArray) parser.parse(new FileReader("./src/main/resources/users.json"));


        boolean found = false;

        for (int i = 0; i < jsonUserArray.size(); i++) {
            JSONObject usersArray = (JSONObject) jsonUserArray.get(i);

            String userName = usersArray.get("username").toString();
            String password = usersArray.get("password").toString();
            String role = usersArray.get("role").toString();

            if (userName.equals(name) && password.equals(pass)) {
                found = true;
                if (role.equals("admin")) {
                    System.out.println("System:> Welcome " + userName + "! Please create new questions in the question bank.");
                    quizBank();
                } else if (role.equals("student")) {
                    quizExam(userName);
                }
                break;
            }
        }
        if(!found){
            System.out.println("System:> Invalid username or password. Please try again.");
        }

    }

    public static void quizBank() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonQuizArray = (JSONArray) parser.parse(new FileReader("./src/main/resources/quizes.json"));

        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("Input your question:");
            String question = scanner.nextLine();
            System.out.print("Input option1:");
            String option1 = scanner.nextLine();
            System.out.print("Input option2:");
            String option2 = scanner.nextLine();
            System.out.print("Input option3:");
            String option3 = scanner.nextLine();
            System.out.print("Input option4:");
            String option4 = scanner.nextLine();
            System.out.print("What is Answer Key?:");
            String answerKey = scanner.nextLine();

            JSONObject quizObj = new JSONObject();
            quizObj.put("question",question);
            quizObj.put("option1",option1);
            quizObj.put("option2",option2);
            quizObj.put("option3",option3);
            quizObj.put("option4",option4);
            quizObj.put("answerKey",answerKey);
            jsonQuizArray.add(quizObj);

            System.out.println("Saved successfully! Do you want to add more questions? (press s for start and q for quit)");
            String nextAction = scanner.nextLine();

            FileWriter writer = new FileWriter("./src/main/resources/quizes.json");
            writer.write(jsonQuizArray.toJSONString());
            writer.flush();
            writer.close();

            if(nextAction.equalsIgnoreCase("q")){
                break;
            }


         }


    }

    public static void quizExam(String userName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonQuizArray = (JSONArray) parser.parse(new FileReader("./src/main/resources/quizes.json"));

        List<JSONObject> quizList = new ArrayList<>();
        for (int i = 0; i < jsonQuizArray.size(); i++) {
            quizList.add((JSONObject) jsonQuizArray.get(i));
        }


        Collections.shuffle(quizList);

        System.out.println("System:> Welcome " + userName + " to the quiz! We will throw you 10 questions. Each MCQ mark is 1 and no negative marking. Are you ready? Press 's' to start.");
        Scanner scanner = new Scanner(System.in);
        String nextAction = scanner.nextLine();
        int marks=0;

        if(nextAction.equalsIgnoreCase("s")) {

            for (int i = 0; i < 10; i++) {
                JSONObject quizObj = quizList.get(i);
                System.out.println("Question " + (i + 1) +": "+ quizObj.get("question"));
                System.out.println("Option1" +" "+ quizObj.get("option1"));
                System.out.println("Option2" +" "+ quizObj.get("option2"));
                System.out.println("Option3" +" "+ quizObj.get("option3"));
                System.out.println("Option4" +" "+ quizObj.get("option4"));

                System.out.print("Enter your Answer: ");
                String answer = scanner.nextLine();

                if(answer.equals(quizObj.get("answerKey"))){
                    marks++;
                }


            }
        }

        if(marks>=8){
            System.out.println("Excellent! You have got " +marks+" out of 10");
        } else if (marks>=5 && marks<8) {
            System.out.println("Good You have got " +marks+" out of 10");
        } else if (marks>=2 && marks<5) {
            System.out.println("Very Poor! You have got " +marks+" out of 10");
        }else{
            System.out.println("Very Sorry you are failed. You have got " +marks+" out of 10");
        }

        System.out.println("Would you like to start again? press s for start or q for quit");
        String nextAction2 = scanner.nextLine();
        if(nextAction2.contains("s")){
            quizExam(userName);
        }


    }
}

   /* public static void createJsonList() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonUserArray = (JSONArray) parser.parse(new FileReader("./src/main/resources/users.json"));
        JSONObject adminObj = new JSONObject();
        adminObj.put("username","admin");
        adminObj.put("password","1234");
        adminObj.put("role","admin");
        jsonUserArray.add(adminObj);

        JSONObject studentObj = new JSONObject();
        studentObj.put("username","naib");
        studentObj.put("password","1234");
        studentObj.put("role","student");
        jsonUserArray.add(studentObj);

        FileWriter writer = new FileWriter("./src/main/resources/users.json");
        writer.write(jsonUserArray.toJSONString());
        writer.flush();
        writer.close();

    }*/






