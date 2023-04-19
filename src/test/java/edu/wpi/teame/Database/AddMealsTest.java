package edu.wpi.teame.Database;

import edu.wpi.teame.entities.MealRequestData;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AddMealsTest {
  @Test
  public void addMeals() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<MealRequestData> meal = SQLRepo.INSTANCE.getMealRequestsList();

    SQLRepo.INSTANCE.addMealRequest(
        new MealRequestData(
            "jamie",
            "beta room",
            "2023-06-07",
            "3:12PM",
            "trump",
            "BLT",
            "tapas",
            "orange juice",
            "allergic to albert as well",
            "",
            MealRequestData.Status.PENDING));
    SQLRepo.INSTANCE.addMealRequest(
        new MealRequestData(
            "joseph",
            "fugma room",
            "2023-09-07",
            "3:12PM",
            "lana roads",
            "pshh special",
            "tapas",
            "vodka 200 proof",
            "allergic to albert too",
            "",
            MealRequestData.Status.PENDING));
    SQLRepo.INSTANCE.addMealRequest(
        new MealRequestData(
            "mich",
            "sigma room",
            "2023-05-17",
            "3:12PM",
            "the classic big d diyar",
            "halal burger",
            "tapas",
            "apple juice",
            "allergic to albert",
            "",
            MealRequestData.Status.PENDING));
    SQLRepo.INSTANCE.addMealRequest(
        new MealRequestData(
            "kevin",
            "alpha room",
            "2023-05-27",
            "3:12PM",
            "ben dover",
            "idk",
            "tapas",
            "apple juice",
            "allergic to albert",
            "",
            MealRequestData.Status.PENDING));
  }

  @Test
  public void deleteMeals() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<MealRequestData> meal = SQLRepo.INSTANCE.getMealRequestsList();

    SQLRepo.INSTANCE.deleteMealRequest(
        new MealRequestData(
            "braeden",
            "ligma room",
            "2023-05-07",
            "3:12PM",
            "justin biber",
            "hamburger",
            "tapas",
            "apple juice",
            "allergic to albert",
            "",
            MealRequestData.Status.PENDING));
    SQLRepo.INSTANCE.deleteMealRequest(
        new MealRequestData(
            "jamie",
            "beta room",
            "2023-06-07",
            "3:12PM",
            "trump",
            "BLT",
            "tapas",
            "orange juice",
            "allergic to albert as well",
            "",
            MealRequestData.Status.PENDING));
    SQLRepo.INSTANCE.deleteMealRequest(
        new MealRequestData(
            "joseph",
            "fugma room",
            "2023-09-07",
            "3:12PM",
            "lana roads",
            "pshh special",
            "tapas",
            "vodka 200 proof",
            "allergic to albert too",
            "",
            MealRequestData.Status.PENDING));
    SQLRepo.INSTANCE.deleteMealRequest(
        new MealRequestData(
            "mich",
            "sigma room",
            "2023-05-17",
            "3:12PM",
            "the classic big d diyar",
            "halal burger",
            "tapas",
            "apple juice",
            "allergic to albert",
            "",
            MealRequestData.Status.PENDING));
    SQLRepo.INSTANCE.deleteMealRequest(
        new MealRequestData(
            "kevin",
            "alpha room",
            "2023-05-27",
            "3:12PM",
            "ben dover",
            "idk",
            "tapas",
            "apple juice",
            "allergic to albert",
            "",
            MealRequestData.Status.PENDING));
  }
}
