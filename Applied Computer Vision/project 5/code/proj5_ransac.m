%Main  Routine for single object tracker
% CS696, homework assignment 5 (HA5): tracking a single object over time
% Input: a short sequence of images of an object (e.g. mug);  an initial box of the object in the first image; 
% Output: object boxes in all the images.

% Assumption: only translation deformation over the bounding box ( you
% might consider the other deformations, e.g. scale, shear, rotation, or
% affine, see Lecture_10_II.


%% global variables
strDirName='seq1'; % name of sequence
%strDirName='seq2';

eucl_error = 0; % calculate euclidean distance error
n = 1;
% load groundtruth box; 
load([strDirName '.mat'],'gd'); 
% generate demo video; 
bOutputVideo=true;
% Each box is described with a rectangle  [xmin ymin width height] 
%arrRect=zeros(4,length(D)); % box sequence to solve

%% Get the initial box from the first image
%initial box, form groundtruth annotation
startBox=gd{1};
D=dir([strDirName '/*.jpg']);
%first image
I=imread([strDirName '/' D(1).name ]);

% Each box is described with a rectangle  [xmin ymin width height] 
arrRect=zeros(4,length(D)); % box sequence to solve

%visualize the initial box;
figure(1), 
imshow(I), hold on;
gx=startBox(:,1);
gy=startBox(:,2);
plot([gx(1) gx(2) gx(2) gx(1) gx(1)],[gy(1) gy(1) gy(2) gy(2) gy(1)],'.-','LineWidth',5, 'Color','g');
title('initial box');

 
%initialize
box1=gd{1};
%convert groundtruth to the rectangle format [xmin ymin width height] 
arrRect(:,1)=[box1(1,1) box1(1,2) box1(2,1)-box1(1,1) box1(2,2)-box1(1,2)];

%% For every the other image, use the following scripts to track object at time t
% step 1: according to the bounding-box at time t-1 (or the first image), crop image objects 
% from the images t-1 and t. 
% step 2: detect interest points in both of the two  cropped images; 
% step 3: extract features for every interest point and find the
% correspondences between two cropped images; 
% step 4: estimate translation displacement (your own code)
% step 5: calculate tracking error: euclidean distance between the predicted central
%point and ground-truth central point (your own code)
% step 6: write images overlaid with boxes in video sequence. Set
% bOutputVideo to be true;

if bOutputVideo
    vHandle = VideoWriter([strDirName '_results.avi']);
    open(vHandle);
end
% for every the other image, estimate the bounding box 
for i=2:length(D)
    % current image
    I=imread([strDirName '/' D(i).name ]);
    % to solve rectCur
    rectCur=zeros(1,4); %rect [xmin ymin width height] 
    
    % previous image    
    %Method 1: use the box at the begining, 
%     Ip=imread([strDirName '/' D(1).name ]);
%     rectPre=arrRect(:,1); 
    
    %Method 2: use the box at the previous time (t-1)
    Ip=imread([strDirName '/' D(i-1).name ]); 
    rectPre=arrRect(:,i-1); % use the box at the previous time (t-1)
    
    %%  step 1: crop images of object
    % crop previous image Ip
    I1=imcrop(Ip,rectPre);    
    % crop the current image I 
    I2=imcrop(I,rectPre);    
    
    % visualize the detected box in the previous
    figure(4), imshow(Ip), hold on;
    gx=[rectPre(1); rectPre(1)+rectPre(3)];
    gy=[rectPre(2); rectPre(2)+rectPre(4)];     
    plot([gx(1) gx(2) gx(2) gx(1) gx(1)],[gy(1) gy(1) gy(2) gy(2) gy(1)],'.-','LineWidth',5, 'Color','b');
    title('detected box');
    title('previous image');
    
    
    %Resize the cropped images and apply Gaussian filtering
    sfactor=5.0;
    I1=rgb2gray(imresize(I1,sfactor));
    I2=rgb2gray(imresize(I2,sfactor));
    % Gaussian filtering
    sigma=5;
    I1 = imgaussfilt(I1,sigma);
    I2 = imgaussfilt(I2,sigma);
    
    %% step 2: detect interest points, i.e., corners.
    points1 = detectHarrisFeatures(I1);
    points2 = detectHarrisFeatures(I2);
    
    %% step 3: extract featurs and Match the features.    
    [features1,valid_points1] = extractFeatures(I1,points1);
    [features2,valid_points2] = extractFeatures(I2,points2);    
    indexPairs = matchFeatures(features1,features2);
    numMatch = size(indexPairs, 1);
   
    % Retrieve the locations of the corresponding points for each image.
    matchedPoints1 = valid_points1(indexPairs(:,1),:);
    matchedPoints2 = valid_points2(indexPairs(:,2),:);
    
    %Visualize the corresponding points
    figure(2); clf; ax = axes;
    showMatchedFeatures(I1,I2,matchedPoints1,matchedPoints2,'montage','Parent',ax);
    
    % IMPORTANT: re-size the localization of the matched points
    matchedPoints1.Location=matchedPoints1.Location/sfactor;
    matchedPoints2.Location=matchedPoints2.Location/sfactor;
    
    %% step-4: estimate the translation displacement (your own code)
    
    %%%%%%%%%% start of placeholder, use the first pair of matched points to estimate translation
    %translation
    
    %%%%%%%%%%%%%%%%%%%%%%% RANSAC ALGORITHM %%%%%%%%%%%%%%%%%%%%%%%%%
    
    %% The input to the algorithm is:
    % Pixel locations in the first img
    image1_points = matchedPoints1.Location;
    image1_points(:,3) = 1; % Add homogeneous coordinate

    image2_points = matchedPoints2.Location;% Pixel locations in the second img
    image2_points(:,3) = 1;

    base_points = ones(n, 3); % Points we want to match
    input_points = ones(n, 3); % Points to be matched against

    best_model = eye(3);
    best_error = 0;
    prev_consensus = 0; % Number of inliers in the previous best model
    
    %% Main loop
   
    k = 100; % - the number of iterations to run
    t = 0.005; % - the threshold for the square distance for a point to be considered as a match
    verbose = true;
for ii = 0:k
    % Take n random points from the first img and their matches in the
    % second image
    
    % Generate random array of size n with unrepeated indices up to
    % numMatch
    rand_indices = randperm(numMatch, n);
    
    for j = 1:n
        base_points(j, :) = image1_points(rand_indices(j), :);
        input_points(j, :) = image2_points(rand_indices(j), :);
    end
    
    % Create A matrix using the data
    A = makeAmatrix(base_points, input_points);
    
    % Solve the equations using SVD
    [~, ~, V] = svd(A);
    
    % The affine matrix transformation is the last column of the V matrix
    % transposed
    maybe_model = reshape(V(:, end), [3, 3])';
    
    %  transformation with all the points - Check how good it is
    consensus_set = 0;
    total_error = 0;
    for j = 1:numMatch
        % Transform the point in image1 using the model TO Image2 
        image1PointTrans = maybe_model * image1_points(j, :)';
        %Make sure the last coordinate is homogeneus
        image1PointTrans = image1PointTrans / image1PointTrans(3);
        distError = norm(image2_points(j, :) - image1PointTrans');
        if distError < t
            consensus_set = consensus_set + 1;
            total_error = total_error + distError;
        end
    end
    
    % Save this transformation if it includes more points in the consensus
    % or the same number of points but with less error
    if consensus_set >= prev_consensus
        if consensus_set > prev_consensus
            if verbose
                fprintf('Improving the model, points match %d, prev mean error %2.2f, current mean error %2.2f\n', ...
                    consensus_set, best_error/prev_consensus, total_error/consensus_set);
            end
            best_model = maybe_model;
            best_error = total_error;
            prev_consensus = consensus_set;
        else if total_error < best_error
                if verbose
                    fprintf('Improving the model, points match %d, prev mean error %2.2f, current mean error %2.2f\n', ...
                        consensus_set, best_error/prev_consensus, total_error/consensus_set);
                end
                best_model = maybe_model;
                best_error = total_error;
                prev_consensus = consensus_set;
            end
        end
    end
end

%% Convert the model into an affine transformation matrix

% Force 3,3 element to be 1
best_model = best_model / best_model(3,3);

% Force bottom two values to be 0, they should already be close to zero but
% they are not, due to numerical errors in the homography calculation and 
% in the previous normalization step
best_model(3,1:2) = 0;

% Matlab affine transformation matrices are transposed, [0, 0, 1] in the   
% last column instead of in the last row
%best_model = best_model;
vdash = best_model;

    %bounding box at the current time
    rectCur(1:2)=rectPre(1:2)-vdash(1:2,1); % 
    rectCur(3:4)=rectPre(3:4); % no scale change over time in this assignment
    %%%%%%%%%% end of place holder 
    
    % output variables
    arrRect(:,i)=rectCur;
    
    %visualize the new box
    figure(3); clf;
    imshow(I), hold on; 
    gx=[rectCur(1); rectCur(1)+rectCur(3)];
    gy=[rectCur(2); rectCur(2)+rectCur(4)];     
    plot([gx(1) gx(2) gx(2) gx(1) gx(1)],[gy(1) gy(1) gy(2) gy(2) gy(1)],'.-','LineWidth',5, 'Color','r');
    title('detected box');
    
    
    % visualize the groundtruth box
    
    boxGD=gd{i};
	rectGD=[boxGD(1,1) boxGD(1,2) boxGD(2,1)-boxGD(1,1) boxGD(2,2)-boxGD(1,2)];
    gx=[rectGD(1); rectGD(1)+rectGD(3)];
    gy=[rectGD(2); rectGD(2)+rectGD(4)];     
    plot([gx(1) gx(2) gx(2) gx(1) gx(1)],[gy(1) gy(1) gy(2) gy(2) gy(1)],'.-','LineWidth',5, 'Color','g');
    title('groundtruth box');
    
     %% step-5: Calculate tracking error: euclidean distance between the predicted box and ground-truth box
     
     % euclidean distance between rectCur and rectGD for each image
     rectCenterXGD = (rectGD(1) + rectGD(3))/2;
     rectCenterYGD = (rectGD(2) +rectGD(4))/2;
     rectCenterXCur = (rectCur(1) + rectCur(3))/2;
     rectCenterYCur = (rectCur(2) +rectCur(4))/2;
     euclideanDistance = mean(sqrt((rectCenterXGD-rectCenterXCur)^2+(rectCenterYGD-rectCenterYCur)^2));
     eucl_error = eucl_error + (euclideanDistance/i);
     
     
     %% step-6: 
     if bOutputVideo
         disp('dump result...');
         frame = getframe;
         writeVideo(vHandle,frame);
     end
 disp(i);
 pause(1/20);   
end
%dump image sequences
disp('average overall error using RANSAC ...');
disp(eucl_error/50);
if bOutputVideo
close(vHandle);
end
%display(euclError/50);







