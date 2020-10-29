public class DrawingFigure {
    public static void drawFigure(int n) {
    	for(int i=1; i<=n; i++)
	        System.out.println("/".repeat(4*(6-i))+"*".repeat(8*(i-1))+"\\".repeat(4*(6-i)));
    }
}
