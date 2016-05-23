%This feature representation is described in the  lecture
%materials, and Szeliski chapter 14.

function image_feats = get_bags_of_sifts(image_paths)
% image_paths is an N x 1 cell array of strings where each string is an
% image path on the file system.

% This function assumes that 'vocab.mat' exists and contains an N x 128
% matrix 'vocab' where each row is a kmeans centroid or visual word. This
% matrix is saved to disk rather than passed in a parameter to avoid
% recomputing the vocabulary every time at significant expense.

% the output variable image_feats is an N x d matrix, where d is the dimensionality of the
% feature representation. In this case, d will equal the number of clusters
% or equivalently the number of entries in each image's histogram.

% You will want to construct SIFT features here in the same way you
% did in build_vocabulary.m ( using the same function vl_sift() by defualt, following the same sampling size and step, except for possibly changing the sampling
% rate) and then assign each local feature to its nearest cluster center
% and build a histogram indicating how many times each cluster was used.
% Don't forget to normalize the histogram, or else a larger image with more
% SIFT features will look very different from a smaller version of the same
% image.

%{
Useful functions:
[locations, SIFT_features] = vl_dsift(img) 
 http://www.vlfeat.org/matlab/vl_dsift.html
 locations is a 2 x n list list of locations, which can be used for extra
  credit if you are constructing a "spatial pyramid".
 SIFT_features is a 128 x N matrix of SIFT features
  note: there are step, bin size, and smoothing parameters you can
  manipulate for vl_dsift(). We recommend debugging with the 'fast'
  parameter. This approximate version of SIFT is about 20 times faster to
  compute. Also, be sure not to use the default value of step size. It will
  be very slow and you'll see relatively little performance gain from
  extremely dense sampling. You are welcome to use your own SIFT feature
  code! It will probably be slower, though.

D = vl_alldist2(X,Y) 
   http://www.vlfeat.org/matlab/vl_alldist2.html
    returns the pairwise distance matrix D of the columns of X and Y. 
    D(i,j) = sum (X(:,i) - Y(:,j)).^2
    Note that vl_feat represents points as columns vs this code (and Matlab
    in general) represents points as rows. So you probably want to use the
    transpose operator '  You can use this to figure out the closest
    cluster center for every SIFT feature. You could easily code this
    yourself, but vl_alldist2 tends to be much faster.

Or:

For speed, you might want to play with a KD-tree algorithm (we found it
reduced computation time modestly.) vl_feat includes functions for building
and using KD-trees.
 http://www.vlfeat.org/matlab/vl_kdtreebuild.html

%}

% ALTERNATE BETWEEN METHOD 1 AND METHOD 2 - TO SEE SPEED DIFFERNECE AND
% PERFORMANCE DIFFERENCE 
load('vocab.mat')
vocab_size = size(vocab, 1);

%% For every image in image_paths
%add your own code to assign each local feature to its nearest cluster
%center in vocab. 
%build a histogram indicating how many times each cluster was used.

% num_of_images = size(image_paths, 1);
% image_feats = zeros(num_of_images, vocab_size);
% vocab_inv = vocab';
% k = 10;
% parfor i = 1 : num_of_images
%     img = im2single(imread(image_paths{i}));
%     [~, features] = vl_dsift(img, 'Step', 10);
%     features = single(features);
%     distance_table = vl_alldist2(vocab_inv, features);
%     [~, index] = sort(distance_table);
%     feat_count = zeros(vocab_size, 1);
%     for j = 1 : vocab_size
%         feat_count(index(1 : k, j)) = feat_count(index(1 : k, j)) + 1;
%     end
%     feat_count = feat_count / norm(feat_count);
%     feat_count = feat_count';
%     
%     image_feats(i, :) = feat_count;
% end
% For each image
% Get sizes and declare feature matrix








%% using KD - trees - TO INCREASE SPEED 

% % size of histogram or num_features
d = vocab_size;
n = length(image_paths);

image_feats = ones(n, d);
    
% build kdtree for distance querying
kdtree = vl_kdtreebuild(vocab', 'NumTrees', 1);

for i = 1:n
    image = single(imread(image_paths{i}));
    
     % Get the locations and descriptors for the image
     % with a fast dense sift with fixed size and step
     % descriptors = 128 x num_features_found
     % locations = 2 x num_features_found
     [~, descriptors] = vl_dsift(image, 'step', 8, 'size', 4, 'fast');
        
     % Find the closest vocab given this image's descriptors
     % each descriptor is found closest to the distance in the vocab matrix
     % min finds the closes of the descriptor to the vocab
     % min_indices = 1 x num_features_found with values from
     % 1..vocab_size
        
     rand_descriptors = descriptors;
        
     [min_indices, ~] = vl_kdtreequery(kdtree, vocab', single(rand_descriptors));
        
     % Build histogram from min_indices
     count_histogram = hist(single(min_indices), d);
     image_feats(i, :) = count_histogram ./ size(rand_descriptors, 2);
 end

end
