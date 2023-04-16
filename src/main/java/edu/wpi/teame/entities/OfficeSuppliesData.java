package edu.wpi.teame.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.NoSuchElementException;

public class OfficeSuppliesData {
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
    @Getter @Setter private String officeSupply;

    @Getter @Setter private Status requestStatus;
    @Getter @Setter private int quantity;
    @Getter @Setter private String assignedStaff;


    public OfficeSuppliesData(String name, String room, Status requestStatus, String deliveryTime, String officeSupply, int q, String assignedStaff){
        this.name =  name;
        this.room = room;
        this.quantity = q;
        this.requestStatus = requestStatus;
        this.deliveryTime = deliveryTime;
        this.officeSupply = officeSupply;
        this.assignedStaff = assignedStaff;
    }

}
