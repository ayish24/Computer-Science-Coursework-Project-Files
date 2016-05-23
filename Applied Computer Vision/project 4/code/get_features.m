% Local Feature Stencil Code
 
% Returns a set of feature descriptors for a given set of interest points. 

% 'image' can be grayscale or color, your choice.
% 'x' and 'y' are nx1 vectors of x and y coordinates of interest points.
%   The local features should be centered at x and y.
% 'feature_width', in pixels, is the local feature width. You can assume
%   that feature_width will be a multiple of 4 (i.e. every cell of your
%   local SIFT-like feature will have an integer width and height).
% If you want to detect and describe features at multiple scales or
% particular orientations you can add input arguments.

% 'features' is the array of computed features. It should have the
%   following size: [length(x) x feature dimensionality] (e.g. 128 for
%   standard SIFT)

function [features] = get_features(image, x, y, feature_width)

% To start with, you might want to simply use normalized patches as your
% local feature. This is very simple to code and works OK. However, to get
% full credit you will need to implement the more effective SIFT descriptor
% (See Szeliski 4.1.2 or the original publications at
% http://www.cs.ubc.ca/~lowe/keypoints/)

% Your implementation does not need to exactly match the SIFT reference.
% Here are the key properties your (baseline) descriptor should have:
%  (1) a 4x4 grid of cells, each feature_width/4.
%  (2) each cell should have a histogram of the local distribution of
%    gradients in 8 orientations. Appending these histograms together will
%    give you 4x4 x 8 = 128 dimensions.
%  (3) Each feature should be normalized to unit length
%
% You do not need to perform the interpolation in which each gradient
% measurement contributes to multiple orientation bins in multiple cells
% As described in Szeliski, a single gradient measurement creates a
% weighted contribution to the 4 nearest cells and the 2 nearest
% orientation bins within each cell, for 8 total contributions. This type
% of interpolation probably will help, though.

% You do not have to explicitly compute the gradient orientation at each
% pixel (although you are free to do so). You can instead filter with
% oriented filters (e.g. a filter that responds to edges with a specific
% orientation). All of your SIFT-like feature can be constructed entirely
% from filtering fairly quickly in this way.

% You do not need to do the normalize -> threshold -> normalize again
% operation as detailed in Szeliski and the SIFT paper. It can help, though.

% Another simple trick which can help is to raise each element of the final
% feature vector to some power that is less than one. This is not required,
% though.

% Placeholder that you can delete. Empty features.
angle_bins = -180:45:180;
	features = zeros(length(x), 128);

	% for each keypoint
	for i = 1 : length(x)
	    % Get window
	    window = image(y(i) - feature_width/2 : y(i) + feature_width/2 - 1, ...
	                   x(i) - feature_width/2 : x(i) + feature_width/2 - 1);
	    
	    % Get gradient of window
	    [gmag, gdir] = imgradient(window); 
	    
	    % Weigh magnitudes with gaussian
	    gmag_weighted = imfilter(gmag, fspecial('gaussian', 10, sqrt(feature_width/2)));
	    
	    % Transform to cells
	    gmag_cols = im2col(gmag_weighted, [feature_width/4, feature_width/4], 'distinct');
	    gdir_cols = im2col(gdir, [feature_width/4, feature_width/4], 'distinct');
	    
	    % for each cell in 4x4 array
	    descriptor = zeros(1, 128);
	    for j = 1 : size(gdir_cols, 1)
	        col = gdir_cols(:, j);
	        [~, inds] = histc(col, angle_bins);
	        
	        buckets = zeros(1, 8); % Sum magnitudes for each histogram bucket
	        for k = 1 : length(inds)
	            buckets(inds(k)) = buckets(inds(k)) + gmag_cols(k, j);
	        end
	        
	        start_ind = (j - 1) * 8 + 1; % Compute start and end indices into descriptor
	        end_ind = (j - 1) * 8 + 8;
	        descriptor(1, start_ind : end_ind) = buckets;
	    end
	    
	    descriptor = descriptor / norm(descriptor); % Normalize descriptor
	    features(i, :) = descriptor; % Add to features
	end

	% Trick: raise each element of feature matrix to number < 1
	power = 0.8;
	features = features .^ power;

end








