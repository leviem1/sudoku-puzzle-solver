import java.util.ArrayList;
import java.util.List;

/**
 * Created by Levi Muniz on 11/15/17.
 * Copyright (c) Levi Muniz. All Rights Reserved.
 */

/*
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class SudukoBoard {
    private boolean isSolved;
    private List<List<List<Integer>>> board;
    private List<List<Integer>> regions;


    public SudukoBoard() {
        isSolved = false;
        board = new ArrayList<>();
        regions = new ArrayList<>();

        generateBoard();
    }

    public SudukoBoard(List<List<Integer>> known) {
        this();
        fillKnown(known);
    }

    private void generateBoard() {
        for (int i = 0; i < 9; i++) {
            board.add(new ArrayList<>());
            List<List<Integer>> row = board.get(i);

            for (int j = 0; j < 9; j++) {
                row.add(new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)));
            }

            regions.add(new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)));
        }
    }

    private List<Integer> getRegionPossibilities(int x, int y) {
        if (x < 3 && y < 3) {
            return regions.get(0);
        } else if (x >= 3 && x < 6 && y < 3) {
            return regions.get(1);
        } else if (x >= 6 && y < 3) {
            return regions.get(2);
        } else if (x < 3 && y < 6) {
            return regions.get(3);
        } else if (x >= 3 && x < 6 && y < 6) {
            return regions.get(4);
        } else if (x >= 6 && y < 6) {
            return regions.get(5);
        } else if (x < 3 && y >= 6) {
            return regions.get(6);
        } else if (x >= 3 && x < 6 && y >= 6) {
            return regions.get(7);
        } else if (x >= 6 && y >= 6) {
            return regions.get(8);
        } else {
            return new ArrayList<>();
        }
    }

    private void removeFromRegionPossibilities(int x, int y, int value) {
        getRegionPossibilities(x, y).remove(Integer.valueOf(value));
    }

    public void fillKnown(List<List<Integer>> known) {
        for (List<Integer> point : known) {
            int x = point.get(1);
            int y = point.get(0);
            int value = point.get(2);

            board.get(y).set(x, new ArrayList<>(List.of(value)));
            removeFromRegionPossibilities(x, y, value);
        }
    }

    @SuppressWarnings("Duplicates")
    public void solve() {
        int failures = 0;

        while (failures < 18 && !isSolved) {
            boolean tempIsSolved = true;

            //Attempt to solve for row
            for (int i = 0; i < 9; i++) {
                List<Integer> rowPossibilities = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));

                //Create a list of used values that cannot exist as possibility for row
                for (int j = 0 ; j < 9; j++) {
                    List<Integer> cell = board.get(i).get(j);

                    if (cell.size() == 1) {
                        rowPossibilities.remove(cell.get(0));
                        removeFromRegionPossibilities(j, i, cell.get(0));
                    } else {
                        tempIsSolved = false;
                    }
                }

                isSolved = tempIsSolved;

                //Delete impossibilities from row's cells
                for (int j = 0; j < 9; j++) {
                    List<Integer> cell = board.get(i).get(j);

                    if (cell.size() != 1) {
                        cell.retainAll(rowPossibilities);
                        cell.retainAll(getRegionPossibilities(j, i));
                    }
                }
            }

            //Attempt to solve for column
            for (int i = 0; i < 9; i++) {
                List<Integer> columnPossibilities = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));

                //Create a list of used values that cannot exist as possibility for column
                for (int j = 0; j < 9; j++) {
                    List<Integer> cell = board.get(j).get(i);

                    if (cell.size() == 1) {
                        columnPossibilities.remove(cell.get(0));
                        removeFromRegionPossibilities(i, j, cell.get(0));
                    }
                }

                for (int j = 0; j < 9; j++) {
                    List<Integer> cell = board.get(j).get(i);

                    if (cell.size() != 1) {
                        cell.retainAll(columnPossibilities);
                        cell.retainAll(getRegionPossibilities(i, j));
                    }
                }
            }

            failures++;
        }
    }

    public boolean isSolved() {
        return isSolved;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (List<List<Integer>> row : board) {
            for (List<Integer> cell : row) {
                sb.append(cell).append(" ");
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}
