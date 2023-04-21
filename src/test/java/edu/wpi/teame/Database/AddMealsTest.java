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
            "Joseph",
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
            "Joseph",
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
            "Joseph",
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
            "Joseph",
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

    SQLRepo.INSTANCE.deleteServiceRequest(
        new MealRequestData(
            1,
            "jamie",
            "beta room",
            "2023-06-07",
            "3:12PM",
            "Jamie",
            "BLT",
            "tapas",
            "orange juice",
            "allergic to albert as well",
            "",
            MealRequestData.Status.PENDING));
    SQLRepo.INSTANCE.deleteServiceRequest(
        new MealRequestData(
            2,
            "joseph",
            "fugma room",
            "2023-09-07",
            "3:12PM",
            "Jamie",
            "pshh special",
            "tapas",
            "vodka 200 proof",
            "allergic to albert too",
            "",
            MealRequestData.Status.PENDING));
    SQLRepo.INSTANCE.deleteServiceRequest(
        new MealRequestData(
            3,
            "mich",
            "sigma room",
            "2023-05-17",
            "3:12PM",
            "Diyar",
            "halal burger",
            "tapas",
            "apple juice",
            "allergic to albert",
            "",
            MealRequestData.Status.PENDING));
    SQLRepo.INSTANCE.deleteServiceRequest(
        new MealRequestData(
            4,
            "kevin",
            "alpha room",
            "2023-05-27",
            "3:12PM",
            "Diyar",
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
            "Joseph",
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

    SQLRepo.INSTANCE.deleteServiceRequest(
        new FlowerRequestData(
            1,
            "jamie",
            "beta room",
            "2023-06-07",
            "3:12PM",
            "Megan",
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

    SQLRepo.INSTANCE.addServiceRequest(
        new FurnitureRequestData(
            0,
            "jamie",
            "beta room",
            "2023-06-07",
            "3:12PM",
            "Joseph",
            "BLT",
            "tapas",
            FurnitureRequestData.Status.PENDING));

    SQLRepo.INSTANCE.deleteServiceRequest(
        new FurnitureRequestData(
            1,
            "jamie",
            "beta room",
            "2023-06-07",
            "3:12PM",
            "Joseph",
            "BLT",
            "tapas",
            FurnitureRequestData.Status.PENDING));
    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void addConference() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<ConferenceRequestData> conference = SQLRepo.INSTANCE.getConfList();

    SQLRepo.INSTANCE.addServiceRequest(
        new ConferenceRequestData(
            0,
            "jamie",
            "beta room",
            "2023-06-07",
            "3:12PM",
            "Joseph",
            "12345678901011",
            "tapas",
            ConferenceRequestData.Status.DONE));

    SQLRepo.INSTANCE.deleteServiceRequest(
        new ConferenceRequestData(
            1,
            "jamie",
            "beta room",
            "2023-06-07",
            "3:12PM",
            "Joseph",
            "12345678901011",
            "tapas",
            ConferenceRequestData.Status.DONE));
    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void addOfficeSupplies() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<OfficeSuppliesData> conference = SQLRepo.INSTANCE.getOfficeSupplyList();

    SQLRepo.INSTANCE.addServiceRequest(
        new OfficeSuppliesData(
            0,
            "jamie",
            "alpha my c",
            "2023-06-07",
            "3:12PM",
            "Joseph",
            "a crayon",
            "1",
            "hello jam",
            OfficeSuppliesData.Status.DONE));
    SQLRepo.INSTANCE.deleteServiceRequest(
        new OfficeSuppliesData(
            1,
            "jamie",
            "alpha my c",
            "2023-06-07",
            "3:12PM",
            "Joseph",
            "a crayon",
            "1",
            "hello jam",
            OfficeSuppliesData.Status.DONE));
    SQLRepo.INSTANCE.exitDatabaseProgram();
  }
}
