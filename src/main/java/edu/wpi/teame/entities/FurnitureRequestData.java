package edu.wpi.teame.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.NoSuchElementException;

public class FurnitureRequestData {
    public enum Status {
        PENDING,
        IN_PROGRESS,
        DONE;

        public static String statusToString(ServiceRequestData.Status st) {
            return getString(st);
        }

        public static String getString(ServiceRequestData.Status st) {
            switch (st) {
                case PENDING:
                    return "PENDING";
                case IN_PROGRESS:
                    return "IN_PROGRESS";
                case DONE:
                    return "DONE";
                default:
                    throw new NoSuchElementException("No such status found");
            }
        }

        public static Status stringToStatus(String st) {
            switch (st) {
                case "PENDING":
                    return PENDING;
                case "IN_PROGRESS":
                    return IN_PROGRESS;
                case "DONE":
                    return DONE;
                default:
                    throw new NoSuchElementException("No such status found");
            }
        }

    }

    @Getter @Setter private String name;
    @Getter @Setter private String room;
    @Getter @Setter private String deliveryTime;
    @Getter @Setter private String deliveryDate;
    @Getter @Setter private int requestID;
    @Getter @Setter private String furnitureType;

    @Getter @Setter private String notes;

    @Getter @Setter private Status requestStatus;
    @Getter @Setter private String assignedStaff;


    public FurnitureRequestData(int requestID, String name, String room, String deliveryDate, String deliveryTime, String staff, String furnitureType, String notes, Status requestStatus){
        this.requestID = requestID;
        this.name =  name;
        this.room = room;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.assignedStaff = staff;
        this.furnitureType = furnitureType;
        this.notes = notes;
        this.requestStatus = requestStatus;
    }

}
