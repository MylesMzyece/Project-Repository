public class Heap<T> {
    private T[] heapArray;
    private int [] priorityValues;
    private int length; //add 1 each time something is added
    private int arraySize; //ask ab size of heap

    //constructor for heap with Type T Data and Int for the priority values
    public Heap(int arraySize){
        heapArray = (T[]) new Object[arraySize];
        priorityValues= new int[arraySize];
    }

    //method to insert and add to heap
    public void insert (T data, int weight){
        //int used to track length of heap in order to double its size if it reaches capacity
        int max= heapArray.length;

        //checks if heap arrays need to be resized
        if(length+1>max){
           //increase size of array
            T[] heapArray2 = (T[]) new Object[2*length];
            int [] priorityValues2 = new int[length*2];
            //copies values from smaller arrays to larger arrays
            for(int i=0; i<length; i++){
                priorityValues2[i]=priorityValues[i];
                heapArray2[i]=heapArray[i];
            }
            //switches larger and smaller arrays
            priorityValues=priorityValues2;
            heapArray=heapArray2;
        }
        //adds new data and weight to Heap Arrays
        heapArray[length]=data;
        priorityValues[length]=weight;
        //heapifies value added at last index
        heapify(length);
        //iterates the length to next open spot
        length++;
    }


    private void heapify(int index){
        if(index!=0){
            int tempIndex = index;
            if(priorityValues[index]<priorityValues[getParentIndex(index)]){
                tempIndex = getParentIndex(index);

                swap(index, tempIndex);

                heapify(tempIndex);
            }
        }
    }

    //
    private void percolate(int index) {
        int leftChild = getLeftChildIndex(index);
        int rightChild = getRightChildIndex(index);
        int smallest = index;

        // Find the lowest weight/priority among parent, left child, and right child
        if (leftChild < length && priorityValues[leftChild] < priorityValues[smallest]) {
            smallest = leftChild;
        }
        if (rightChild < length && priorityValues[rightChild] < priorityValues[smallest]) {
            smallest = rightChild;
        }

        // If the smallest is not the current node, swap and recurse
        if (smallest != index) {
            swap(index, smallest);
            percolate(smallest);
        }
    }

    private void swap(int index1, int index2){
        T temp = heapArray[index1];
        int tempWeight = priorityValues[index1];

        heapArray[index1] = heapArray[index2];
        heapArray[index2] =  temp;

        priorityValues[index1] = priorityValues[index2];
        priorityValues[index2] = tempWeight;
    }


    //tells if a index is a leaf
    public boolean isLeaf(int index){
        return (index>length/2);
    }

    //returns min values (peeks)
    public <T> Object getMinData(){
        return heapArray[0];
    }

    public int getMinWeight(){
        return priorityValues[0];
    }

    public void extractMin (){ //removes root node and sets last index to top the percolates down
        if(length==0){
            return;
        }

        priorityValues[0] = priorityValues[length-1];
        heapArray[0] = heapArray[length-1];

        priorityValues[length-1]=0;
        heapArray[length-1]=null;

        length--;
        percolate(0);
    }

    public int getParent(int index){ //should this return the index or the actual value
        return priorityValues[(index-1)/2];
    }
    public int getLeftChild(int index){ //is index the correct input here?
        return priorityValues[(index*2)+1];
    }
    public int getRightChild(int index){
        return priorityValues[(index*2)+2];
    }

    public int getParentIndex(int index){ //used for internal methods dealing specifically w/ indexes
        if(index%2!=0){
            return ((index-1)/2);
        }
        else return((index-2)/2);
    }
    public int getLeftChildIndex(int index){ //is index the correct input here?
        return (index*2)+1;
    }
    public int getRightChildIndex(int index){
        return (index*2)+2;
    }
    public <T> Object getData(int index){
        return heapArray[index];
    }

    public int getWeight(int index){
        return priorityValues[index];
    }
}
class GenericArray<T> {
    //code taken from GPT because you can't naturally make an array of generic type T
    private T[] array;

    @SuppressWarnings("unchecked")
    public GenericArray(int size) {
        // Create an array of Object and cast it to T[]
        this.array = (T[]) new Object[size];
    }
    //returns an array of generic type T
    public T[] getArray() {
        return array;
    }
}