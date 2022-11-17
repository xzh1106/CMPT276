package AI;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    public int col = 100;
    public int row = 100;

    void nodeTest(int col, int row){
        this.col = col;
        this.row = row;

        assertEquals(100, this.col);
        assertEquals(100, this.row);
    }

}