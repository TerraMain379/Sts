class Main: Program{
    public static void main(){
        Location loc = new Location();
        loc.x = 10;
        loc.y = 5;
        loc.z = 1;
    }
}
class Location <- IVector{
    public double x;
    public double y;
    public double z;

    public Location(){
        x=0;
        y=0;
        y=0;
    }
}
enum GameMode{
    CREATIVE,
    SURVIVAL;
}

