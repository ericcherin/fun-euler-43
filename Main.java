public class Main {

    static int fcount;
    static int bcount;

    public static void main(String[] args) {
        System.out.println(forward());
        System.out.println(backward());

        System.out.println("forward time: " + fcount + " backwards time: " + bcount);
    }

    public static long backward(){
        return bh(9, new boolean[10], new int[10], new int[]{2,3,5,7,11,13,17});
    }

    //@param digits represents the 09 number.
    //@param index is the current digit we are choosing for the 09 number.
    public static long bh(int index, boolean[] visited, int[] digits, int[] primes){
        //The recursive calls make a tree, where the node holds a state: index, visited, and digits.
        //Everytime we visit a new node, iterate the counter.
        bcount++;

        //we no longer have any digits left. Convert the array of digits into a number.
        if(index == -1){
            long ret = 0;
            long mult = 1;

            for(int i = digits.length - 1; i >= 0; i--){
                ret += digits[i] * mult;
                mult *= 10;
            }

            return ret;
        }

        long sum = 0;
        int nextIndex = 0;

        //the index + 1 represents the number of choices that we have remaining.
        //when we start at index of 9, we will have 10 choices because we can choose the digits 0 - 9.
        for(int i = 0; i <= index; i++){

            nextIndex = getNextIndex(visited, nextIndex + 1);
            visited[nextIndex] = true;
            digits[index] = nextIndex;

            //when index == 0, we do not need to calculate the first 3 digits.
            //when index > 7 we do not yet have 2 digits to the right of the current index.
            if(index > 7 || index < 1){
                sum += bh(index - 1, visited, digits, primes);
            }
            else{
                //convert the array of 3 digits into a number. The array begins with index and ends with index + 2.
                int subSum = 100 * digits[index] + 10 * digits[index + 1] + digits[index + 2];
                if(subSum % primes[index - 1] == 0){
                    sum += bh(index - 1, visited, digits, primes);
                }
            }


            visited[nextIndex] = false;
        }

        return sum;
    }

    public static long forward(){
        return fh(0, new boolean[10], new int[10], new int[]{2,3,5,7,11,13,17});
    }

    public static long fh(int index, boolean[] visited, int[] digits, int[] primes){
        fcount++;
        if(index == digits.length){
            long ret = 0;
            long mult = 1;

            for(int i = digits.length - 1; i >= 0; i--){
                ret += digits[i] * mult;
                mult *= 10;
            }

            return ret;
        }

        long sum = 0;
        int nextIndex = 0;

        for(int i = 0; i < 10 - index; i++){


            nextIndex = getNextIndex(visited, nextIndex + 1);
            visited[nextIndex] = true;
            digits[index] = nextIndex;

            if(index > 2){
                int subSum = 100 * digits[index - 2] + 10 * digits[index - 1] + digits[index];
                if(subSum % primes[index - 3] == 0){
                    sum += fh(index + 1, visited, digits, primes);
                }
            }
            else{
                sum += fh(index + 1, visited, digits, primes);
            }

            visited[nextIndex] = false;
        }

        return sum;
    }

    //Returns the next index that has not yet been visited starting with @param start.
    //If we reach the end of the array before finding a next index, we will start from the begining.
    public static int getNextIndex(boolean[] visited, int start){
        for(int i = 0; i < visited.length; i++){
            int index = (start + i ) % visited.length;
            if(!visited[index]){
                return index;
            }
        }
        return -1;
    }
}
