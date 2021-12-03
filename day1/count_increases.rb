# get the input as a collection
# input = "tiny_input.txt"
input = "input.txt"
depth_readings = IO.readlines(input).map(&:to_i)

increases_so_far = 0
previous_depth = depth_readings[0] + 1

depth_readings.each do |depth|
    increases_so_far += 1 if depth > previous_depth
    previous_depth = depth
end

puts increases_so_far


# probably a collection method where we can get 2 at a time - reduce?
# oh boy do I hate this!
puts (depth_readings).inject([0, depth_readings[0] + 1]) { |bad_array, depth|
    increases_so_far = bad_array[0]
    previous_depth = bad_array[1]
    increases_so_far += 1 if depth > previous_depth
    [increases_so_far, depth]
}[0]