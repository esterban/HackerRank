package leetcode.multiplystrings;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solution {
    static class Value {
        byte[] bcdRepresentation = new byte[401];
        int length = 0;

        public Value(String numString) {
            length = numString.length();

            for (int index = length - 1; index >= 0; --index) {
                int digitIndex = bcdRepresentation.length - length + index;
                char digitChar = numString.charAt(index);

                byte valueInt = (byte) (digitChar - '0');
                bcdRepresentation[digitIndex] = valueInt;
            }
        }

        private Value() {
            length = 1;
        }

        public Value product(Value other) {
            Value accumulator = new Value();

            if (length == 1 && bcdRepresentation[bcdRepresentation.length - 1] == 0) {
                return accumulator;
            }

            if (other.length == 1 && other.bcdRepresentation[other.bcdRepresentation.length - 1] == 0) {
                return accumulator;
            }

            int leftShiftPlaces = 0;

            for (int index = bcdRepresentation.length - 1; index > bcdRepresentation.length - 1 - other.length; --index) {
                byte otherDigit = other.bcdRepresentation[index];

                Value digitMulValue = mulByDigit(otherDigit);
                digitMulValue = digitMulValue.decimalLeftShift(leftShiftPlaces);

                accumulator = accumulator.add(digitMulValue);
                ++leftShiftPlaces;

                // if (leftShiftPlaces > 1) {
                //     // return digitMulValue;
                //     return accumulator;
                // }
            }

            return accumulator;
        }

        @Override
        public String toString() {
            return IntStream.range(bcdRepresentation.length - length, bcdRepresentation.length).mapToObj(i -> String.valueOf(bcdRepresentation[i])).collect(Collectors.joining(""));

            // return "Value{" +
            //         "bcdRepresentation=" + Arrays.toString(Arrays.copyOfRange(bcdRepresentation, bcdRepresentation.length - length - 3, bcdRepresentation.length)) +
            //         ", length=" + length +
            //         '}';
        }

        public Value mulByDigit(byte digit) {
            Value result = new Value();
            result.length = length;

            byte decimalOverflow = 0;

            int index = bcdRepresentation.length - 1;

            for (; index >= bcdRepresentation.length - length; --index) {
                byte thisDigit = bcdRepresentation[index];
                byte digitProduct = (byte) ((thisDigit * digit) + decimalOverflow);

                byte digitValue = (byte) (digitProduct % 10);

                result.bcdRepresentation[index] = digitValue;

                decimalOverflow = (byte) (digitProduct / 10);
            }

            if (decimalOverflow > 0) {
                result.bcdRepresentation[index] = decimalOverflow;
                ++result.length;
            }

            return result;
        }

        public Value add(Value otherValue) {
            Value result = new Value();
            result.length = Math.max(length, otherValue.length);

//            System.out.println("Adding this = " + toString() + " , other = " + otherValue);

            int index = bcdRepresentation.length - 1;

            byte overflow = 0;

            for (; index > bcdRepresentation.length - 1 - result.length; --index) {
                byte thisDigit = bcdRepresentation[index];
                byte otherDigit = otherValue.bcdRepresentation[index];

                byte digitSum = (byte) (thisDigit + otherDigit + overflow);

//                System.out.println("digitSum = " + digitSum);

                overflow = (byte) (digitSum / 10);
                digitSum = (byte) (digitSum % 10);

                if (overflow > 0 && index == bcdRepresentation.length - 1 - result.length) {
                    ++result.length;
                }

                result.bcdRepresentation[index] = digitSum;
            }

            // System.out.println("overflow = " + overflow);

            if (overflow > 0) {
                result.bcdRepresentation[index] = overflow;
//                System.out.println("add overflow result.length = " + result.length);

                ++result.length;

//                System.out.println("add overflow result.length = " + result.length);
            }

//            System.out.println("adding result = " + result);

            return result;
        }


        public Value decimalLeftShift(int shiftPlaces) {
            // System.out.println("Left shifting " + toString() + " by places = " + shiftPlaces);

            Value result = new Value();
            result.length = length + shiftPlaces;

            System.arraycopy(bcdRepresentation, bcdRepresentation.length - length, result.bcdRepresentation, bcdRepresentation.length - length - shiftPlaces, length);

            if (shiftPlaces > 0) {
                Arrays.fill(result.bcdRepresentation, bcdRepresentation.length - shiftPlaces, bcdRepresentation.length - 1, (byte) 0);
            }

            return result;
        }

    }

    public String multiply(String num1, String num2) {
        Value value1 = new Value(num1);
        Value value2 = new Value(num2);
        Value product = value1.product(value2);

        return product.toString();
    }
}