% Local Feature Stencil Code


% 'features1' and 'features2' are the n x feature dimensionality features
%   from the two images.
% If you want to include geometric verification in this stage, you can add
% the x and y locations of the features as additional inputs.
%
% 'matches' is a k x 2 matrix, where k is the number of matches. The first
%   column is an index in features 1, the second column is an index
%   in features2. 
% 'Confidences' is a k x 1 matrix with a real valued confidence for every
%   match.
% 'matches' and 'confidences' can empty, e.g. 0x2 and 0x1.
function [matches, confidences] = match_features(features1, features2)

% This function does not need to be symmetric (e.g. it can produce
% different numbers of matches depending on the order of the arguments).

% For extra credit you can implement various forms of spatial verification of matches.


% 
%< Placeholder that you can delete. Random matches and confidences
%pleae detect this following paragraph and implement your own codes; 
%'matches':and 'confidences' according to the
%near neighbor based matching algorithm. 

    num_features1 = size(features1, 1);
	num_features2 = size(features2, 1);
	matches = zeros(num_features1, 2);
	confidences = zeros(num_features1, 1);

	% find the euclidian distance between each pair of descriptors in feature
	% space
	distances = zeros(num_features2, num_features1);
	for i = 1 : num_features1
	    for j = 1 : num_features2
	        distances(j, i) = norm(features1(i, :) - features2(j, :));
	    end
	end

	threshold = 0.8;
	[sorted_distances, inds] = sort(distances); % sort distances in ascending order

	% calculate ratio of nearest neighbor to second nearest neighbor
	% if above threshold, add to matches list and save ratio as confidence value
	for i = 1 : num_features1
	    ratio = sorted_distances(1, i) / sorted_distances(2, i);
	    if ratio < threshold
	        matches(i, :) = [i, inds(1, i)];
	        confidences(i) = 1 - ratio;
	    end
	end

	% keep only those that matched
	match_inds = find(confidences > 0);
	matches = matches(match_inds, :);
	confidences = confidences(match_inds);

	% Sort the matches so that the most confident onces are at the top of the list
	[confidences, ind] = sort(confidences, 'descend');
	matches = matches(ind, :);

end