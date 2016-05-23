function descriptors = get_pyramid_descriptors_for_image(image)

    L = 2;
    l0_d = 256;
    l1_d = 128;
    l2_d = 64;

    % Resize to 256x256
    image = imresizecrop(image, [l0_d, l0_d]);

    % L = [0, 1, 2]

    % Descriptors at lev
    % descriptors = 128 x num_features_found
    % locations = 2 x num_features_found

    % l = 0
    l0_descriptors = get_pyramid_descriptor_for_level(image, 0, l0_d);

    % l = 1
    l1_descriptors = get_pyramid_descriptor_for_level(image, 1, l1_d);
    % subtract duplicate descriptors
    l1_descriptors = setdiff(l1_descriptors', l0_descriptors', 'rows')';
    
    % l = 2
    l2_descriptors = get_pyramid_descriptor_for_level(image, 2, l2_d);
    l2_descriptors = setdiff(l2_descriptors', l1_descriptors', 'rows')';

    % Weight the levels and combine
    descriptors = [l0_descriptors .* 1/4, l1_descriptors .* 1/4, l2_descriptors .* 1/2];
    
end

function descriptors = get_pyramid_descriptor_for_level(image, level, step)

    max_sift_step = 8;
    max_sift_size = 8;
    sift_step = max_sift_step * 2 * 1/pow2(level+1); % 8, 4, 2;
    sift_size = max_sift_size * 2 * 1/pow2(level+1); % 8, 4, 2;

    for i = 0:level
        for j = 0:level

            i_start = step*i + 1;
            i_end = step*(i + 1);
            j_start = step*j + 1;
            j_end = step*(j + 1);


            [~, current_descriptors] = vl_dsift(image(i_start:i_end, j_start:j_end), 'step', sift_step, 'size', sift_size, 'fast');

            num_features = size(current_descriptors, 1);
            num_descriptors = size(current_descriptors, 2);

            if ~exist('descriptors', 'var')
                descriptors = zeros(num_features, num_descriptors*pow2(level));
            end

            % place current increment of descriptors in descriptors
            % matrix
            % 128xd
            current_descriptor_index_start = i*num_descriptors + j*num_descriptors + 1;
            current_descriptor_index_end = current_descriptor_index_start + num_descriptors -1;
            descriptors(:, current_descriptor_index_start:current_descriptor_index_end) = current_descriptors;
        end
    end
end
