package by.bsuir.pokerface.entity.room;

import by.bsuir.pokerface.entity.room.impl.*;

public enum RoomStateStorage implements RoomState {
    WAITING(new WaitingState()),
    PREFLOP(new PreflopState()),
    FLOP(new FlopState()),
    TURN(new TurnState()),
    RIVER(new RiverState()),
    FINAL(new FinalState());


    private final RoomState state;

    RoomStateStorage(RoomState state) {
        this.state = state;
    }


    @Override
    public void onStart(Room room) {
        state.onStart(room);
    }

    @Override
    public RoomState nextState() {
        return state.nextState();
    }
}
