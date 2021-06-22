package lab1;
import java.util.Scanner;
public class MovieDriver {

public static void main(String[] args) {
	
 Scanner news=new Scanner(System.in);
    Movie film=new Movie();
    
while(true) {
		System.out.println("Enter the name of a movie "); // This is to ask the user for the title of the movie
   String title=news.nextLine();  
     film.setTitle(title); // displays the title
     
     	System.out.println("Enter the rating of the movie");//This is for asking the user to enter movie rating
   String rating=news.nextLine();
     film.setRating(rating); // this displays the rating 
     
     	System.out.println("Enter the number of tickets sold for this movie");// This asks the user what the rating of the movie is
int soldTickets=Integer.parseInt(news.nextLine());
     film.setSoldTickets(soldTickets); // displays the number of tickets sold  
  
     	System.out.println(film.toString());//printing movie data
  
     	System.out.println("Do you want to enter another? (y or n)"); // asking the user  to input another movie or not
   String option =news.nextLine();
   
if(option.equalsIgnoreCase("y")) // When the user says yes, then the process starts all over again
           continue;

else
           break;
       }
       System.out.println("Goodbye"); // the end of the survey
   }

}