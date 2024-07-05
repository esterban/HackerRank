package hackerrank.algorithms.strings.stringsimilarity;

import java.io.*;
import java.util.stream.IntStream;

class Result {

    /*
     * Complete the 'stringSimilarity' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING s as parameter.
     */

    public char firstChar;
    public char secondChar;
    public char[] sChars;

    public static int stringSimilarity(String s) {
        Result result = new Result();
        int sLength = s.length();

        result.firstChar = s.charAt(0);
        result.secondChar = sLength > 1 ? s.charAt(1) : 0;

        int similaritySum = 0;

        result.sChars = s.toCharArray();

        for (int suffixStartPosition = 0; suffixStartPosition <= sLength; ++suffixStartPosition) {
            int suffixOffset = suffixStartPosition;

            similaritySum += similaritySumChars(suffixOffset, sLength - suffixOffset, result.sChars);
//            similaritySum += result.similaritySumChars(suffixOffset, sLength - suffixOffset);
        }

        return similaritySum;
    }

    public static int similaritySumChars(int suffixOffset, int suffixLength, char[] b) {
        int stringEnd = 0;

        for (; stringEnd < suffixLength; ++stringEnd) {
            if (b[stringEnd + suffixOffset] != b[stringEnd]) {
                return stringEnd;
            }
        }

        return stringEnd;
    }

    public int similaritySumChars(int suffixOffset, int suffixLength) {

        if (suffixLength == 0 || firstChar != sChars[suffixOffset]) {
            return 0;
        }

        if (suffixLength == 1 || secondChar != sChars[suffixOffset + 1]) {
            return 1;
        }

        int stringEnd = 2;

        for (; stringEnd < suffixLength; ++stringEnd) {
            if (sChars[stringEnd + suffixOffset] != sChars[stringEnd]) {
                return stringEnd;
            }
        }

        return stringEnd;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static int stringSimilarityShorts(String s) {
        Result result = new Result();
        int sLength = s.length();

        int similaritySum = 0;

        result.sChars = s.toCharArray();

        short[] shortChars = result.convertToShortArray(s.getBytes(), 0);
        short[] shortCharsShift1 = result.convertToShortArray(s.getBytes(), 1);

        int sShortRemainder = sLength % 2;
        int sShortLength = sLength / 2 + sShortRemainder;

        for (int suffixStartPosition = 0; suffixStartPosition <= sShortLength; ++suffixStartPosition) {
            int suffixOffsetInChars = suffixStartPosition * 2;

            similaritySum += similaritySumShorts(suffixOffsetInChars, sLength - suffixOffsetInChars, shortChars, shortChars);
            similaritySum += similaritySumShorts(suffixOffsetInChars + 1, sLength - suffixOffsetInChars - 1, shortChars, shortCharsShift1);
        }


        return similaritySum;
    }

    public static int similaritySumShorts(int suffixOffsetInChars, int suffixLengthInChars, short[] sShorts, short[] suffixShorts) {
        int stringPosition = 0;
        int suffixOffsetInShorts = suffixOffsetInChars / 2;
        int suffixOffsetRemainder = suffixOffsetInChars % 2;

        int suffixLengthShorts = suffixLengthInChars / 2;
        int suffixLengthShortsRemainder = suffixLengthInChars % 2;

        for (; stringPosition < suffixLengthShorts; ++stringPosition) {
            if (sShorts[stringPosition] != suffixShorts[stringPosition + suffixOffsetInShorts]) {
                if ((sShorts[stringPosition] & 0xff) == (suffixShorts[stringPosition + suffixOffsetInShorts] & 0xff)) {
                    return stringPosition * 2 + 1;
                }

                return stringPosition * 2;
            }
        }

        if (suffixLengthShortsRemainder == 1) {
            if ((sShorts[stringPosition] & 0x00ff) == (suffixShorts[stringPosition + suffixOffsetInShorts] & 0x00ff)) {
                return stringPosition * 2 + 1;
            }
        }

        return stringPosition * 2;
    }

    public short[] convertToShortArray(byte[] chars, int bytesShift) {
        int shiftedLength = chars.length - bytesShift;

        int shortArrayLength = shiftedLength / 2;
        int shortArrayRemainder = shiftedLength % 2;

        short[] shorts = new short[shortArrayLength + shortArrayRemainder];
        int shortsLength = shorts.length;
        int shortIndex = 0;

        for (; shortIndex < shortsLength; ++shortIndex) {
            int charIndex = shortIndex * 2;

            if (charIndex + 1 == shiftedLength) {
                shorts[shortIndex] = (short) (chars[charIndex + 0 + bytesShift]);
            } else {
                shorts[shortIndex] = (short) (chars[charIndex + 0 + bytesShift] + (chars[charIndex + 1 + bytesShift] << 8));
            }
        }

        if (bytesShift == 1) {
            int charIndex = shortIndex * 2 + 1;

            if (charIndex < shiftedLength) {
                shorts[shortIndex] = chars[charIndex];
            }
        }

        return shorts;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS ****
    // INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS ****
    // INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS **** INTEGERS ****
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static long stringSimilarityInts(String s) {
        Result result = new Result();
        int sLength = s.length();

        long similaritySum = 0;

//        result.sChars = s.toCharArray();
        byte[] sBytes = s.getBytes();

        int[] intChars = result.convertToIntArray(sBytes, 0);
        int[] intCharsShift1 = result.convertToIntArray(sBytes, 1);
        int[] intCharsShift2 = result.convertToIntArray(sBytes, 2);
        int[] intCharsShift3 = result.convertToIntArray(sBytes, 3);

        int sIntRemainder = sLength % 4;
        int sIntLength = sLength / 4 + sIntRemainder;

        for (int suffixStartPosition = 0; suffixStartPosition <= sIntLength; ++suffixStartPosition) {
            int suffixOffsetInChars = suffixStartPosition * 4;

            similaritySum += similaritySumInts(suffixOffsetInChars, sLength - suffixOffsetInChars, intChars, intChars);
            similaritySum += similaritySumInts(suffixOffsetInChars + 1, sLength - suffixOffsetInChars - 1, intChars, intCharsShift1);
            similaritySum += similaritySumInts(suffixOffsetInChars + 2, sLength - suffixOffsetInChars - 2, intChars, intCharsShift2);
            similaritySum += similaritySumInts(suffixOffsetInChars + 3, sLength - suffixOffsetInChars - 3, intChars, intCharsShift3);
        }

        return similaritySum;
    }

    public static int similaritySumInts(int suffixOffsetInChars, int suffixLengthInChars, int[] sInts, int[] suffixInts) {
        int stringPosition = 0;
        int suffixOffsetInInts = suffixOffsetInChars / 4;

        int suffixLengthInts = suffixLengthInChars / 4;
        int suffixLengthIntsRemainder = suffixLengthInChars % 4;

        for (; stringPosition < suffixLengthInts; ++stringPosition) {
            if (sInts[stringPosition] != suffixInts[stringPosition + suffixOffsetInInts]) {
                if ((sInts[stringPosition] & 0xffffff) == (suffixInts[stringPosition + suffixOffsetInInts] & 0xffffff)) {
                    return stringPosition * 4 + 3;
                }

                if ((sInts[stringPosition] & 0xffff) == (suffixInts[stringPosition + suffixOffsetInInts] & 0xffff)) {
                    return stringPosition * 4 + 2;
                }

                if ((sInts[stringPosition] & 0xff) == (suffixInts[stringPosition + suffixOffsetInInts] & 0xff)) {
                    return stringPosition * 4 + 1;
                }

                return stringPosition * 4;
            }
        }

        if (suffixLengthIntsRemainder == 1) {
            if ((sInts[stringPosition] & 0xff) == (suffixInts[stringPosition + suffixOffsetInInts] & 0xff)) {
                return stringPosition * 4 + 1;
            }
        } else if (suffixLengthIntsRemainder == 2) {
            if ((sInts[stringPosition] & 0xffff) == (suffixInts[stringPosition + suffixOffsetInInts] & 0xffff)) {
                return stringPosition * 4 + 2;
            }

            if ((sInts[stringPosition] & 0x00ff) == (suffixInts[stringPosition + suffixOffsetInInts] & 0x00ff)) {
                return stringPosition * 4 + 1;
            }
        } else if (suffixLengthIntsRemainder == 3) {
            if ((sInts[stringPosition] & 0xffffff) == (suffixInts[stringPosition + suffixOffsetInInts] & 0xffffff)) {
                return stringPosition * 4 + 3;
            }

            if ((sInts[stringPosition] & 0xffff) == (suffixInts[stringPosition + suffixOffsetInInts] & 0xffff)) {
                return stringPosition * 4 + 2;
            }

            if ((sInts[stringPosition] & 0xff) == (suffixInts[stringPosition + suffixOffsetInInts] & 0xff)) {
                return stringPosition * 4 + 1;
            }
        }

        return stringPosition * 4;
    }


    public int[] convertToIntArray(byte[] chars, int bytesShift) {
        int shiftedLength = chars.length - bytesShift;

        int intArrayLength = shiftedLength / 4;
        int intArrayRemainder = shiftedLength % 4;
        int intArrayExtraLength = intArrayRemainder > 0 ? 1 : 0;

        int[] ints = new int[intArrayLength + intArrayExtraLength];
        int intIndex = 0;

        for (; intIndex < intArrayLength; ++intIndex) {
            int charIndex = intIndex * 4;

            ints[intIndex] = (int) ((chars[charIndex + 0 + bytesShift]) + (chars[charIndex + 1 + bytesShift] << 8) + (chars[charIndex + 2 + bytesShift] << 16)) + (chars[charIndex + 3 + bytesShift] << 24);
        }

        int charIndex = intIndex * 4 + bytesShift;

        if (intArrayRemainder == 1) {
            ints[intArrayLength] = chars[charIndex];
        } else if (intArrayRemainder == 2) {
            ints[intArrayLength] = chars[charIndex] + (chars[charIndex + 1] << 8);
        } else if (intArrayRemainder == 3) {
            ints[intArrayLength] = chars[charIndex] + (chars[charIndex + 1] << 8) + (chars[charIndex + 2] << 16);
        } else if (intArrayRemainder == 4) {
            ints[intArrayLength] = chars[charIndex] + (chars[charIndex + 1] << 8) + (chars[charIndex + 2] << 16) + (chars[charIndex + 3] << 24);
        }

        return ints;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS ***
    // LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS ***
    // LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS *** LONGS ***
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static long stringSimilarityLongs(String s) {
        Result result = new Result();
        int sLength = s.length();

        long similaritySum = 0;

//        result.sChars = s.toCharArray();
        byte[] sBytes = s.getBytes();

        long[] longChars = result.convertToLongArray(sBytes, 0);
        long[] longCharsShift1 = result.convertToLongArray(sBytes, 1);
        long[] longCharsShift2 = result.convertToLongArray(sBytes, 2);
        long[] longCharsShift3 = result.convertToLongArray(sBytes, 3);
        long[] longCharsShift4 = result.convertToLongArray(sBytes, 4);
        long[] longCharsShift5 = result.convertToLongArray(sBytes, 5);
        long[] longCharsShift6 = result.convertToLongArray(sBytes, 6);
        long[] longCharsShift7 = result.convertToLongArray(sBytes, 7);

        int sIntRemainder = sLength % 2;
        int sIntLength = sLength / 2 + sIntRemainder;

        for (int suffixStartPosition = 0; suffixStartPosition <= sIntLength; ++suffixStartPosition) {
            int suffixOffsetInChars = suffixStartPosition * 8;

            similaritySum += similaritySumLongs(suffixOffsetInChars, sLength - suffixOffsetInChars, longChars, longChars);
            similaritySum += similaritySumLongs(suffixOffsetInChars + 1, sLength - suffixOffsetInChars - 1, longChars, longCharsShift1);
            similaritySum += similaritySumLongs(suffixOffsetInChars + 2, sLength - suffixOffsetInChars - 2, longChars, longCharsShift2);
            similaritySum += similaritySumLongs(suffixOffsetInChars + 3, sLength - suffixOffsetInChars - 3, longChars, longCharsShift3);
            similaritySum += similaritySumLongs(suffixOffsetInChars + 4, sLength - suffixOffsetInChars - 4, longChars, longCharsShift4);
            similaritySum += similaritySumLongs(suffixOffsetInChars + 5, sLength - suffixOffsetInChars - 5, longChars, longCharsShift5);
            similaritySum += similaritySumLongs(suffixOffsetInChars + 6, sLength - suffixOffsetInChars - 6, longChars, longCharsShift6);
            similaritySum += similaritySumLongs(suffixOffsetInChars + 7, sLength - suffixOffsetInChars - 7, longChars, longCharsShift7);
        }

        return similaritySum;
    }

    public static int similaritySumLongs(int suffixOffsetInChars, int suffixLengthInChars, long[] sLong, long[] suffixLongs) {
        int stringPosition = 0;
        int suffixOffsetInLongs = suffixOffsetInChars / 8;

        int suffixLengthInLongs = suffixLengthInChars / 8;
        int suffixLengthLongsRemainder = suffixLengthInChars % 8;

        for (; stringPosition < suffixLengthInLongs; ++stringPosition) {
            if (sLong[stringPosition] != suffixLongs[stringPosition + suffixOffsetInLongs]) {
//                if ((sLong[stringPosition] & 0xffffff) == (suffixLongs[stringPosition + suffixOffsetInLongs] & 0xffffff)) {
//                    return stringPosition * 4 + 3;
//                }
//
//                if ((sLong[stringPosition] & 0xffff) == (suffixLongs[stringPosition + suffixOffsetInLongs] & 0xffff)) {
//                    return stringPosition * 4 + 2;
//                }
//
//                if ((sLong[stringPosition] & 0xff) == (suffixLongs[stringPosition + suffixOffsetInLongs] & 0xff)) {
//                    return stringPosition * 4 + 1;
//                }

                int remainderScore = calculateRemainderLong(sLong[stringPosition], suffixLongs[stringPosition + suffixOffsetInLongs], 7);

                return stringPosition * 8 + remainderScore;
            }
        }

        int remainderScore = suffixLengthLongsRemainder <= 0 ? 0 : calculateRemainderLong(sLong[stringPosition], suffixLongs[stringPosition + suffixOffsetInLongs], suffixLengthLongsRemainder);

        return stringPosition * 8 + remainderScore;
    }

    public static int calculateRemainderLong(long a, long b, int remainderLength) {
        switch (remainderLength) {
            case 7:
                if ((a & 0x00ffffffffffffffl) == (b & 0x00ffffffffffffffl)) {
                    return 7;
                }

            case 6:
                if ((a & 0x0000ffffffffffffl) == (b & 0x0000ffffffffffffl)) {
                    return 6;
                }

            case 5:
                if ((a & 0x000000ffffffffffl) == (b & 0x000000ffffffffffl)) {
                    return 5;
                }

            case 4:
                if ((a & 0x00000000ffffffffl) == (b & 0x00000000ffffffffl)) {
                    return 4;
                }

            case 3:
                if ((a & 0xffffff) == (b & 0xffffff)) {
                    return 3;
                }

            case 2:
                if ((a & 0xffff) == (b & 0xffff)) {
                    return 2;
                }

            case 1:
                return (a & 0xff) == (b & 0xff) ? 1 : 0;
        }

        return 0;
    }


    public long[] convertToLongArray(byte[] chars, int bytesShift) {
        int shiftedLength = chars.length - bytesShift;

        int longArrayLength = shiftedLength / 8;
        int longArrayRemainder = shiftedLength % 8;
        int longArrayExtraLength = longArrayRemainder > 0 ? 1 : 0;

        long[] longs = new long[longArrayLength + longArrayExtraLength];
        int longIndex = 0;

        for (; longIndex < longArrayLength; ++longIndex) {
            int charIndex = longIndex * 8;

            longs[longIndex] = (long) (chars[charIndex + 0 + bytesShift]) + (((long) chars[charIndex + 1 + bytesShift]) << 8) +
                    (((long) chars[charIndex + 2 + bytesShift]) << 16) + ((long) (chars[charIndex + 3 + bytesShift]) << 24) +
                    (((long) chars[charIndex + 4 + bytesShift]) << 32) + (((long) chars[charIndex + 5 + bytesShift]) << 40) +
                    (((long) chars[charIndex + 6 + bytesShift]) << 48) + (((long) chars[charIndex + 7 + bytesShift]) << 56);
        }

        int charIndex = longIndex * 8 + bytesShift;

        if (longArrayRemainder == 1) {
            longs[longArrayLength] = chars[charIndex];
        } else if (longArrayRemainder == 2) {
            longs[longArrayLength] = chars[charIndex] + (chars[charIndex + 1] << 8);
        } else if (longArrayRemainder == 3) {
            longs[longArrayLength] = chars[charIndex] + (chars[charIndex + 1] << 8) + (chars[charIndex + 2] << 16);
        } else if (longArrayRemainder == 4) {
            longs[longArrayLength] = chars[charIndex] + (chars[charIndex + 1] << 8) + (chars[charIndex + 2] << 16) + (chars[charIndex + 3] << 24);
        } else if (longArrayRemainder == 5) {
            longs[longArrayLength] = ((long) chars[charIndex]) + (((long) chars[charIndex + 1]) << 8) +
                    (((long) chars[charIndex + 2]) << 16) + (((long) chars[charIndex + 3]) << 24) +
                    (((long) chars[charIndex + 4]) << 32);
        } else if (longArrayRemainder == 6) {
            longs[longArrayLength] = ((long) chars[charIndex]) + (((long) chars[charIndex + 1]) << 8) +
                    (((long) chars[charIndex + 2]) << 16) + (((long) chars[charIndex + 3]) << 24) +
                    (((long) chars[charIndex + 4]) << 32) + (((long) chars[charIndex + 5]) << 40);
        } else if (longArrayRemainder == 7) {
            longs[longArrayLength] = ((long) chars[charIndex]) + (((long) chars[charIndex + 1]) << 8) +
                    (((long) chars[charIndex + 2]) << 16) + (((long) chars[charIndex + 3]) << 24) +
                    (((long) chars[charIndex + 4]) << 32) + (((long) chars[charIndex + 5]) << 40) +
                    (((long) chars[charIndex + 6]) << 48);
        }

        return longs;
    }


}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        long nanoStart = System.nanoTime();

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String s = bufferedReader.readLine();

//                int result = Result.stringSimilarity(s);
//                int result = Result.stringSimilarityShorts(s);
//                long result = Result.stringSimilarityInts(s);
                long result = Result.stringSimilarityLongs(s);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        long nanoEnd = System.nanoTime();

        long nanoDuration = nanoEnd - nanoStart;

        double millisDuration = nanoDuration / 1e6;

        System.out.println("Duration = " + millisDuration + " ms");

        bufferedReader.close();
        bufferedWriter.close();
    }
}
