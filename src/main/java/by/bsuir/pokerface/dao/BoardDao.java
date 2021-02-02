package by.bsuir.pokerface.dao;

import by.bsuir.pokerface.entity.board.Board;
import by.bsuir.pokerface.warehouse.BoardWarehouse;

import java.util.Optional;

public interface BoardDao {
    Optional<Board> getBoardById(int id);
}
