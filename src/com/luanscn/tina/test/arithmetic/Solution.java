package com.luanscn.tina.test.arithmetic;

import com.luanscn.tina.test.util.TestUtil;


public class Solution {
	/*
     * @param triangle: a list of lists of integers
     * @return: An integer, minimum path sum
     */
    public static int minimumTotal(int[][] triangle) {
        // write your code here
        Integer result = 0;
        Integer limit = 0;
        for(int i = 0; i < triangle.length ; i ++){
            int[] temp = triangle[i];
            Integer min = temp[limit];
            if(temp.length > (limit + 1)){
                if(min > temp[limit + 1]){
                    min = temp[limit + 1];
                }
            }
            result += min;
        }
        return result;
    }
    
    public static void main(String[] args) {
    	int[][] triangle = {{-1},{2,3},{1,-1,-3}};
    	TestUtil.Println(minimumTotal(triangle));
	}
}
