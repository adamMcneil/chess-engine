package cheekykoala.pieces;

import cheekykoala.Position;

public class Directions {
    final public static int[] diagonal = {
            -8 - 1,
            -8 + 1,
            8 - 1,
            8 + 1,
    };

    final public static int[] orthogonal = {
            -8,
            8,
            -1,
            1,
    };

    final public static int[] vertical = {
            -8,
            8,
    };

    final public static int[] horizontal = {
            -1,
            1,
    };

    final public static int[] all = {
            -8,
            8,
            -1,
            1,
            -8 - 1,
            -8 + 1,
            8 - 1,
            8 + 1,
    };

    final public static int[] knights = {+17, +15, +10, -6, -15, -17, -10, +6};
}
