package org.example.balloongame;

public interface CommonInterface {

    public static enum GameItem {

        NONE( R.drawable.item_none ), BOMB( R.drawable.item_bomb ), CLOCK( R.drawable.item_clock );

        public int drawableResourceId ;

        GameItem(int drawableResourceId) {
            this.drawableResourceId = drawableResourceId ;
        }
    }
}
