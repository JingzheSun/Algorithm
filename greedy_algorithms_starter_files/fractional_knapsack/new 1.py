
# Uses python3
import sys

def get_optimal_value(capacity, weights, values):
    value = 0.
    # write your code here
    for j in range(n):
        if capacity==0:
            return value
        
        max_ratio = values[0]/weights[0]; max_index = 0
        for i in range(len(weights)-1):
            
            if values[i+1]/weights[i+1] >= values[i]/weights[i]:
                max_ratio = values[i+1]/weights[i+1]
                max_index = i+1
        
            
        if weights[max_index]<capacity:
            a=weights[max_index]
        else:
            a=capacity
        
        value += a*max_ratio
        weights.pop(max_index)
        values.pop(max_index)
        capacity -= a
    
    return value

if __name__ == "__main__":
    data = list(map(int, sys.stdin.read().split()))
    n, capacity = data[0:2]
    values = data[2:(2 * n + 2):2]
    weights = data[3:(2 * n + 2):2]
    opt_value = get_optimal_value(capacity, weights, values)
    print("{:.10f}".format(opt_value))