package edu.wpi.teame.Database;

import edu.wpi.teame.entities.*;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AddMealsTest {
  @Test
  public void addMeals() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<MealRequestData> meal = SQLRepo.INSTANCE.getMealRequestsList();

    SQLRepo.INSTANCE.addMealRequest(
        new MealRequestData(
            0,
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
            0,
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
            0,
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
            0,
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
    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void deleteMeals() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<MealRequestData> meal = SQLRepo.INSTANCE.getMealRequestsList();

    SQLRepo.INSTANCE.deleteMealRequest(
        new MealRequestData(
            1,
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
            2,
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
            3,
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
            4,
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
    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void addFlowers() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    SQLRepo.INSTANCE.addFlowerRequest(
        new FlowerRequestData(
            0,
            "jamie",
            "beta room",
            "2023-06-07",
            "3:12PM",
            "trump",
            "shit",
            "shit",
            "yes",
            "Fuck this Bullshit",
            "",
            FlowerRequestData.Status.PENDING));
    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void deleteFlowers() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    SQLRepo.INSTANCE.deleteFlowerRequest(
        new FlowerRequestData(
            1,
            "jamie",
            "beta room",
            "2023-06-07",
            "3:12PM",
            "trump",
            "shit",
            "shit",
            "yes",
            "Fuck this Bullshit",
            "",
            FlowerRequestData.Status.PENDING));
    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void addFurniture() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<FurnitureRequestData> furniture = SQLRepo.INSTANCE.getFurnitureRequestsList();

    SQLRepo.INSTANCE.addFurnitureRequest(
        new FurnitureRequestData(
            0,
            "jamie",
            "beta room",
            "2023-06-07",
            "3:12PM",
            "trump",
            "BLT",
            "tapas",
            FurnitureRequestData.Status.PENDING));

    SQLRepo.INSTANCE.deleteFurnitureRequest(
        new FurnitureRequestData(
            1,
            "jamie",
            "beta room",
            "2023-06-07",
            "3:12PM",
            "trump",
            "BLT",
            "tapas",
            FurnitureRequestData.Status.PENDING));
    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void addConference() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<ConferenceRequestData> conference = SQLRepo.INSTANCE.getConfList();

    SQLRepo.INSTANCE.addConfRoomRequest(
        new ConferenceRequestData(
            0,
            "jamie",
            "beta room",
            "2023-06-07",
            "3:12PM",
            "trump",
            "12345678901011",
            "tapas",
            ConferenceRequestData.Status.DONE));

    SQLRepo.INSTANCE.deleteConfRoomRequest(
        new ConferenceRequestData(
            1,
            "jamie",
            "beta room",
            "2023-06-07",
            "3:12PM",
            "trump",
            "12345678901011",
            "tapas",
            ConferenceRequestData.Status.DONE));
    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void addOfficeSupplies() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<OfficeSuppliesData> conference = SQLRepo.INSTANCE.getOfficeSupplyList();

    SQLRepo.INSTANCE.addOfficeSupplyRequest(
        new OfficeSuppliesData(
            0,
            "jamie",
            "alpha my c",
            "2023-06-07",
            "3:12PM",
            "jack penis",
            "a crayon",
            "1",
            "hello jam",
            OfficeSuppliesData.Status.DONE));
    SQLRepo.INSTANCE.deleteOfficeSupplyRequest(
        new OfficeSuppliesData(
            1,
            "jamie",
            "alpha my c",
            "2023-06-07",
            "3:12PM",
            "jack penis",
            "a crayon",
            "1",
            "hello jam",
            OfficeSuppliesData.Status.DONE));
    SQLRepo.INSTANCE.exitDatabaseProgram();
  }
}
