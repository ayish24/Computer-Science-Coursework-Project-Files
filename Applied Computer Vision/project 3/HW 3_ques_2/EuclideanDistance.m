
%
% Euclidean distance
%

function error = EuclideanDistance(A,B)

    error = sqrt( sum( (A - B) .^ 2 ) );

end

