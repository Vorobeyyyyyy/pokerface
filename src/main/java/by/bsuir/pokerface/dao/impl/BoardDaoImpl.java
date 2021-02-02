package by.bsuir.pokerface.dao.impl;

import by.bsuir.pokerface.dao.BoardDao;
import by.bsuir.pokerface.entity.board.Board;
import by.bsuir.pokerface.warehouse.BoardWarehouse;

import java.util.List;
import java.util.Optional;

public class BoardDaoImpl implements BoardDao {
    private final static BoardWarehouse warehouse = BoardWarehouse.getInstance();

    private final static BoardDaoImpl instance = new BoardDaoImpl();

    private BoardDaoImpl() {}

    public static BoardDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<Board> getBoardById(int id) {
        return warehouse.stream().filter(o -> o.getId() == id).findFirst();
    }
}
