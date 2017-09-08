package com.google.engedu.puzzle8;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class PuzzleBoard {

    private static final int NUM_TILES = 3;
    private static final int[][] NEIGHBOUR_COORDS = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };
    private ArrayList<PuzzleTile> tiles = new ArrayList<>();
    private ArrayList<PuzzleTile> tiles2 = new ArrayList<>();

    PuzzleTile puzzleTile;
    Bitmap tileImages[] = new Bitmap[NUM_TILES * NUM_TILES];
    Bitmap scaledImages[] = new Bitmap[NUM_TILES * NUM_TILES];

    PuzzleBoard(Bitmap bitmap, int parentWidth) {

        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            tileImages[i] = Bitmap.createBitmap(bitmap,
                    (i % NUM_TILES) * (bitmap.getWidth() / NUM_TILES),
                    (i / NUM_TILES) * (bitmap.getHeight() / NUM_TILES),
                    bitmap.getWidth() / NUM_TILES,
                    bitmap.getHeight() / NUM_TILES);
            scaledImages[i] = Bitmap.createScaledBitmap(tileImages[i], parentWidth / (NUM_TILES * NUM_TILES), parentWidth / (NUM_TILES * NUM_TILES), false);

            System.out.println(i);
            puzzleTile = new PuzzleTile(scaledImages[i], i);
            if (i == NUM_TILES * NUM_TILES - 1) {
                puzzleTile = null;
            }
            tiles.add(i, puzzleTile);
        }
        Log.d("heyu", tiles.toString() + "h");
    }


    public void reset() {
        // Nothing for now but you may have things to reset once you implement the solver.
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return tiles.equals(((PuzzleBoard) o).tiles);
    }

    public void draw(Canvas canvas) {
        if (tiles == null) {
            return;
        }
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                tile.draw(canvas, i % NUM_TILES, i / NUM_TILES);
            }
        }
    }

    public boolean click(float x, float y) {
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                if (tile.isClicked(x, y, i % NUM_TILES, i / NUM_TILES)) {
                    return tryMoving(i % NUM_TILES, i / NUM_TILES);
                }
            }
        }
        return false;
    }

    private boolean tryMoving(int tileX, int tileY) {
        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = tileX + delta[0];
            int nullY = tileY + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)) == null) {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }

        }
        return false;
    }

    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile == null || tile.getNumber() != i)
                return false;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return x + y * NUM_TILES;
    }

    void shuffle() {
       /* Random random = new Random();
        int b[] = new int[NUM_TILES * NUM_TILES];

        for (int i = 0, c = 0; c < NUM_TILES * NUM_TILES; i++) {
            boolean contains = false;
            int n = random.nextInt(NUM_TILES * NUM_TILES);
            for (int j = 0;j<NUM_TILES*NUM_TILES ; j++) {
                if (b[j] == n) {
                    contains = true;
                    break;
                }
            }
            if (contains == false) {
                b[c] = n;
                c++;
            }

        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            tiles2.add(b[i], tiles.get(i));
        }
        tiles=tiles2;*/

    }


    protected void swapTiles(int i, int j) {
        PuzzleTile temp = tiles.get(i);
        tiles.set(i, tiles.get(j));
        tiles.set(j, temp);
    }

    public ArrayList<PuzzleBoard> neighbours() {

        int index[] = new int[NUM_TILES * NUM_TILES];
        PuzzleTile tileArray[] = new PuzzleTile[NUM_TILES * NUM_TILES];
        PuzzleTile empty;
        int emptyIndex = 0;

        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            tileArray[i] = tiles.get(i);
            if (tileArray[i] == null) {
                empty = tileArray[i];
                emptyIndex = i;
            }

        }
               int tempx=0,tempy=0;
        Random random = new Random();
        int decider=0;
        int x = emptyIndex % 3;
        int y = emptyIndex / 3;
        for (; ; ) {
            decider = random.nextInt(4);
            tempx=NEIGHBOUR_COORDS[decider][0];
            tempy=NEIGHBOUR_COORDS[decider][1];
            if ((x+tempx)>=0&&(x+tempx)<=2&&(y+tempy)>=0&&(y+tempy)<=2) {
                int temp=(x+tempx)*3+(y+tempy);
                swapTiles(emptyIndex,temp);
                break;
            }

        }


        return null;
    }

    public int priority() {
        return 0;
    }

}