import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Levi Muniz on 9/19/17.
 * Copyright (c) Levi Muniz. All Rights Reserved.
 */
public class SudokuSolver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<List<Integer>> known = new ArrayList<>();

        System.out.println("x,y,v;x,y,v that x = column, y = row, v = value in quadrant 4");
        String input = scanner.nextLine();

        String[] knownCells = input.split(";");

        //Build list of known values
        for (String cell : knownCells) {
            String[] stringCellProps = cell.split(",");

            List<Integer> cellProps = new ArrayList<>();

            for (String prop : stringCellProps) {
                cellProps.add(Integer.parseInt(prop));
            }

            known.add(cellProps);
        }

        SudukoBoard puzzle = new SudukoBoard(known);

        puzzle.solve();

        if (puzzle.isSolved()) {
            System.out.println("SUCCESS");
        } else {
            System.err.println("FAILURE");
        }

        System.out.println(puzzle);
    }
}
