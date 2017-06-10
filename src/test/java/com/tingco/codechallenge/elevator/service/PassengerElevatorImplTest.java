package com.tingco.codechallenge.elevator.service;

import com.tingco.codechallenge.elevator.enums.ElevatorDirection;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Lorinc Sonnevend
 */
public class PassengerElevatorImplTest {

    private int minFloor = -1;
    private int maxFloor = 10;

    private PassengerElevatorImpl elevator = new PassengerElevatorImpl(1, minFloor, maxFloor);

    @Test
    public void elevatorDirectionIsNoneWhenInitialized() {
        assertEquals("direction in wrong state", ElevatorDirection.NONE, elevator.getDirection());
    }

    @Test
    public void elevatorHasNoPassengersWhenInitialized() {
        assertEquals("passangers are not empty at initialization",true, elevator.getPassengers().isEmpty());
    }


    @Test
    public void elevatorIsOnMinimumFloorWhenInitialized() {
        assertEquals("minFloor is not initialized correctly", minFloor, elevator.currentFloor());
    }

    @Test
    public void elevatorIsNotBusyWhenInitialized() {
        assertEquals("should not be busy",false, elevator.isBusy());
    }

    @Test
    public void moveElevatorToAFloorAboveCurrentPosition() {
        //when
        elevator.moveElevator(10);
        assertEquals("Not moving up", ElevatorDirection.UP, elevator.getDirection());
        assertEquals("Wrong addressed floor", 10, elevator.getAddressedFloor());
        assertEquals("Not Busy", true, elevator.isBusy());
    }

    @Test
    public void cannotMoveAboveLimits() {
        //when
        elevator.moveElevator(11);
        //then
        assertEquals("Is moving", ElevatorDirection.NONE, elevator.getDirection());
        assertEquals("Wrong addressed floor", -999, elevator.getAddressedFloor());
        assertEquals("Busy", false, elevator.isBusy());
    }

    @Test
    public void cannotMoveBellowLimits() {
        //when
        elevator.moveElevator(-10);
        //then
        assertEquals("Is moving", ElevatorDirection.NONE, elevator.getDirection());
        assertEquals("Wrong addressed floor", -999, elevator.getAddressedFloor());
        assertEquals("Busy", false, elevator.isBusy());
    }

    @Test
    public void shouldMoveOneFlootInTheDirectionOfTargetFloor() {
        //when
        elevator.moveElevator(10);
        elevator.operate();
        //then
        assertEquals("Not moving in the right direction", ElevatorDirection.UP, elevator.getDirection());
        assertEquals("Wrong addressed floor", 10, elevator.getAddressedFloor());
        assertEquals("On wrong floor", 0, elevator.currentFloor());
        assertEquals("Not busy", true, elevator.isBusy());
    }

    @Test
    public void shouldStopAtDestination() {
        //when
        elevator.moveElevator(0);
        elevator.operate();
        //then
        assertEquals("Is moving", ElevatorDirection.NONE, elevator.getDirection());
        assertEquals("Wrong addressed floor", -999, elevator.getAddressedFloor());
        assertEquals("On wrong floor", 0, elevator.currentFloor());
        assertEquals("Is busy", false, elevator.isBusy());
    }

    @Test
    public void shouldStopAtDestinationButContinueIfHasMoreInTheQueue() {
        //when
        elevator.moveElevator(0);
        elevator.moveElevator(1);
        elevator.operate();
        //then
        assertEquals("Not moving in the right direction", ElevatorDirection.UP, elevator.getDirection());
        assertEquals("Wrong addressed floor", 1, elevator.getAddressedFloor());
        assertEquals("On wrong floor", 0, elevator.currentFloor());
        assertEquals("Not busy", true, elevator.isBusy());
    }


}