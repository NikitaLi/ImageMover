package com.example.sony.imagemover;

import java.util.ArrayList;

/**
 * Image moving process
 */
class Mover {

    static int sPrevImgID;
    static int sMovesCount;

    static ArrayList<MoveInfo> sMovingHistory = new ArrayList<>();

    static void saveToMovingHistory(int movesCount, int firstFragmentId, int lastFragmentId) {
        sMovingHistory.add(new MoveInfo(movesCount, firstFragmentId, lastFragmentId));
    }
}
