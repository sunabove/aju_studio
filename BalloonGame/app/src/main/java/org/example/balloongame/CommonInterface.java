package org.example.balloongame;

public interface CommonInterface {

    public static enum GameItem {

        NONE( R.drawable.item_none , "" ),
        BOMB( R.drawable.item_bomb , "폭탄" ),
        CLOCK( R.drawable.item_clock , "시계" )

        ;

        public int drawableResourceId ;
        public String itemName ;

        GameItem(int drawableResourceId , String itemName ) {
            this.drawableResourceId = drawableResourceId ;
            this.itemName = itemName ;
        }
    }
}
