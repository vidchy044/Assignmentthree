import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
class Node{
    char data;
    Node next;
    public Node(char data){
        this.data=data;
        this.next=null;
    }
}
class Stack{
    private Node top;
    public Stack(){
        top=null;
    }
    public void push(char data) {
        Node pushnode = new Node(data);
        pushnode.next = top;
        top = pushnode;
    }    
    public boolean isempty(){
        return top == null;
    }
    public char pop() {
        if (isempty()) {
            System.out.println("Stack empty");
            return 0;
        }
        char popnode = top.data;
        top = top.next;
        return popnode;
    }
    public char peek() {
        if (isempty()) {
            throw new IllegalStateException("Stack empty");
        }
        return top.data;
    }    
}
public class Assignmentthree{
    public static boolean operator(char c){
        return (c=='+'||c=='-'||c=='*'||c=='/'||c=='^');
    }
    public static boolean alphaordigit(char c){
        return (Character.isLetterOrDigit(c));
    }
    public static int order(char c){
        if(c=='+'||c=='-'){
            return 1;
        }else if(c=='*'||c=='/'){
            return 2;
        }else if(c=='^'){
            return 3;
        }else{return -1;}
    }
    public static boolean valid(String input){
        int openbracket=0;
        int closebracket=0;

        for(int i=0 ; i<input.length() ; i++){
            char c = input.charAt(i);
            if(c=='('){
                openbracket++;
            }else if(c==')'){closebracket++;}

            if (!(operator(c)||alphaordigit(c)||c=='('||c==')')){
                return false;
            }else if(i>0 && operator(c) && operator(input.charAt(i-1))){
                return false;
            }else if(i>0 && alphaordigit(c) && alphaordigit(input.charAt(i-1))){
                return false;
            }
        }
        return openbracket==closebracket;
    }
    public static String convert(String infix) {
        Stack st = new Stack();
        StringBuilder postfix = new StringBuilder();
    
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            if (alphaordigit(c)) {
                postfix.append(c);
            } else if (c == '(') {
                st.push(c);
            } else if (c == ')') {
                while (st.peek() != '(') {
                    postfix.append(st.pop());
                }
                st.pop();
            } else if (operator(c)) {
                while (!st.isempty() && order(c) <= order(st.peek())) {
                    postfix.append(st.pop());
                }
                st.push(c);
            }
        }
        while (!st.isempty()) {
            postfix.append(st.pop());
        }
        return postfix.toString();
    }
    
    public static void removespace(String filename) {
            File inputFile = new File(filename);
        StringBuilder cleanedContent = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String cleanedLine = line.replaceAll("(?<=\\S)\\s+(?=\\S)", "");
                cleanedContent.append(cleanedLine).append("\n");
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile))) {
                bw.write(cleanedContent.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        File filename = new File("Input1.txt");
        String inputFile = "Input1.txt";
        removespace(inputFile);
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int expressionCount = 1;
            while ((line = br.readLine()) != null) {
                System.out.println("Expression " + expressionCount + " : ");
                System.out.println("Infix exp: " + line);
                if (valid(line)) {
                    System.out.println("Valid");
                    System.out.println("Postfix exp:" + convert(line));
                } else {
                    System.out.println("Not-Valid");
                }
                expressionCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
