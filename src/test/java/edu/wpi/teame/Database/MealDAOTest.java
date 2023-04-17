package edu.wpi.teame.Database;

import edu.wpi.teame.entities.MealRequestData;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MealDAOTest {
    @Test
    public void testGetAddDelete(){
        SQLRepo.INSTANCE.connectToDatabase("teame","teame50");

        List<MealRequestData> meal = SQLRepo.INSTANCE.getMealRequestsList();

        SQLRepo.INSTANCE.addMealRequest(new MealRequestData(1, "joseph", "Cafe", "4/17/2023", "3:12PM","not jamie", "Crepe au jambon", "tapas", "apple cider", "allergic to diyar", "", MealRequestData.Status.PENDING));

        List<MealRequestData> mealRequestAdded = SQLRepo.INSTANCE.getMealRequestsList();
        assertEquals(mealRequestAdded.size(), meal.size() + 1);

        SQLRepo.INSTANCE.deleteMealRequest(new MealRequestData(1, "joseph", "Cafe", "4/17/2023", "3:12PM","not jamie", "Crepe au jambon", "tapas", "apple cider", "allergic to diyar", "", MealRequestData.Status.PENDING));

        List<MealRequestData> mealRequestDeleted = SQLRepo.INSTANCE.getMealRequestsList();
        assertEquals(mealRequestDeleted.size(), meal.size());

        SQLRepo.INSTANCE.exitDatabaseProgram();
    }

    @Test
    public void testUpdate(){
        SQLRepo.INSTANCE.connectToDatabase("teame","teame50");

        MealRequestData mealRequest = new MealRequestData(1, "joseph", "Cafe", "4/17/2023", "3:12PM","not jamie", "Crepe au jambon", "tapas", "apple cider", "allergic to diyar", "", MealRequestData.Status.PENDING);

        SQLRepo.INSTANCE.addMealRequest(mealRequest);
        SQLRepo.INSTANCE.updateMealRequest(mealRequest, "status", "DONE");
        //SQLRepo.INSTANCE.deleteMealRequest(mealRequest);

        SQLRepo.INSTANCE.exitDatabaseProgram();
    }

    @Test
    public void testImportExport(){
        SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

        SQLRepo.INSTANCE.exportToCSV(
                SQLRepo.Table.MEAL_REQUESTS, "C:\\Users\\thesm\\OneDrive\\Desktop\\CS 3733", "MealExport");

        SQLRepo.INSTANCE.importFromCSV(
                SQLRepo.Table.MEAL_REQUESTS,
                "C:\\Users\\thesm\\OneDrive\\Desktop\\CS 3733\\MealExport");

        SQLRepo.INSTANCE.exitDatabaseProgram();
    }

}
