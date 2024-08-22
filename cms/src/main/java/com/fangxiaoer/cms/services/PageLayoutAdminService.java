package com.fangxiaoer.cms.services;

import com.fangxiaoer.cms.models.*;
import com.fangxiaoer.cms.models.enumeration.BlockType;
import com.fangxiaoer.cms.models.enumeration.PageStatus;
import com.fangxiaoer.cms.models.vm.CreateOrUpdatePageLayout;
import com.fangxiaoer.cms.models.vm.QueryPageLayout;
import com.fangxiaoer.cms.repositories.PageLayoutRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PageLayoutAdminService {
    private final PageLayoutRepository pageLayoutRepository;

    public void delete(String id) {
        pageLayoutRepository.deleteById(id);
    }

    public PageLayout update(String id, CreateOrUpdatePageLayout updatePageLayout) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.setTitle(updatePageLayout.title());
                existingPageLayout.setTargetPage(updatePageLayout.targetPage());
                existingPageLayout.setPlatform(updatePageLayout.platform());
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout publish(String id, LocalDateTime startTime, LocalDateTime endTime) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.setStatus(PageStatus.PUBLISHED);
                existingPageLayout.setStartTime(startTime);
                existingPageLayout.setEndTime(endTime);
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout draft(String id) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.setStatus(PageStatus.DRAFT);
                existingPageLayout.setStartTime(null);
                existingPageLayout.setEndTime(null);
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout archive(String id) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.setStatus(PageStatus.ARCHIVED);
                existingPageLayout.setStartTime(null);
                existingPageLayout.setEndTime(null);
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout create(CreateOrUpdatePageLayout createPageLayout) {
        val pageLayout = PageLayout.builder()
            .title(createPageLayout.title())
            .status(PageStatus.DRAFT)
            .targetPage(createPageLayout.targetPage())
            .platform(createPageLayout.platform())
            .config(createPageLayout.config())
            .build();
        return pageLayoutRepository.save(pageLayout);
    }

    public Page<PageLayout> searchPageLayouts(QueryPageLayout queryPageLayout, Pageable pageable) {
        return pageLayoutRepository.findByCriteria(queryPageLayout, pageable);
    }

    public PageLayout addBlock(String id, PageBlock block) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                block.setSort(existingPageLayout.getBlocks().size() + 1);
                existingPageLayout.getBlocks().add(block);
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout insertBlock(String id, Integer sort, PageBlock block) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.getBlocks().stream()
                    .filter(b -> b.getSort() >= sort)
                    .forEach(b -> b.setSort(b.getSort() + 1));
                block.setSort(sort);
                existingPageLayout.getBlocks().add(block);
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout removeBlock(String id, String blockId) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.getBlocks().removeIf(block -> block.getId().equals(blockId));
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout updateBlock(String id, String blockId, PageBlock block) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.getBlocks().stream()
                    .filter(b -> b.getId().equals(blockId))
                    .findFirst()
                    .ifPresent(b -> {
                        b.setTitle(block.getTitle());
                        b.setConfig(block.getConfig());
                    });
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout moveBlockUp(String id, String blockId) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.moveBlockUp(blockId);
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout moveBlockDown(String id, String blockId) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.moveBlockDown(blockId);
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout moveBlockTop(String id, String blockId) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.moveBlockTop(blockId);
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout moveBlockBottom(String id, String blockId) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.moveBlockBottom(blockId);
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout moveBlockTo(String id, String blockId, Integer sort) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.getBlocks().stream()
                    .filter(b -> b.getId().equals(blockId))
                    .findFirst()
                    .ifPresent(b -> {
                        b.setSort(sort);
                    });
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout copyBlock(String id, String blockId) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.getBlocks().stream()
                    .filter(b -> b.getId().equals(blockId))
                    .findFirst()
                    .ifPresent(b -> {
                        val copied = PageBlock.builder()
                            .title(b.getTitle())
                            .type(b.getType())
                            .config(b.getConfig())
                            .data(b.getData())
                            .build();
                        insertBlock(id, b.getSort() + 1, copied);
                    });
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout addBlockData(String id, String blockId, PageBlockData data) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.getBlocks().stream()
                    .filter(b -> b.getId().equals(blockId))
                    .findFirst()
                    .ifPresent(b -> {
                        validateData(b.getType(), data);
                        data.setSort(b.getData().size() + 1);
                        b.getData().add(data);
                    });
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    private void validateData(BlockType type, PageBlockData data) {
        if (data == null || data.getContent() == null) {
            throw new IllegalArgumentException("Data or content cannot be null");
        }

        switch (type) {
            case BANNER:
                if (!(data.getContent() instanceof ImageData)) {
                    throw new IllegalArgumentException("Invalid data type for BANNER. Expected ImageData.");
                }
                // Additional validation for ImageData can be added here
                break;
            case IMAGE_ROW:
                if (!(data.getContent() instanceof ImageData)) {
                    throw new IllegalArgumentException("Invalid data type for IMAGE_ROW. Expected ImageData.");
                }
                // Additional validation for CategoryData can be added here
                break;
            case HOUSE_ROW:
                if (!(data.getContent() instanceof HouseData)) {
                    throw new IllegalArgumentException("Invalid data type for HOUSE. Expected HouseData.");
                }
                // Additional validation for HouseData can be added here
                break;
            case WATERFALL:
                if (!(data.getContent() instanceof CategoryData)) {
                    throw new IllegalArgumentException("Invalid data type for WATERFALL. Expected CategoryData.");
                }
                // Additional validation for HouseData can be added here
                break;
            default:
                throw new IllegalArgumentException("Unsupported BlockType: " + type);
        }
    }


    public PageLayout updateBlockData(String id, String blockId, String dataId, PageBlockData data) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.getBlocks().stream()
                    .filter(b -> b.getId().equals(blockId))
                    .findFirst()
                    .ifPresent(b -> {
                        b.getData().stream()
                            .filter(d -> d.getId().equals(dataId))
                            .findFirst()
                            .ifPresent(d -> {
                                d.setSort(data.getSort());
                                d.setContent(data.getContent());
                            });
                    });
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout removeBlockData(String id, String blockId, String dataId) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.getBlocks().stream()
                    .filter(b -> b.getId().equals(blockId))
                    .findFirst()
                    .ifPresent(b -> {
                        b.getData().removeIf(d -> d.getId().equals(dataId));
                    });
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout moveBlockDataUp(String id, String blockId, String dataId) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.getBlocks().stream()
                    .filter(b -> b.getId().equals(blockId))
                    .findFirst()
                    .ifPresent(b -> {
                        b.moveDataUp(dataId);
                    });
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout moveBlockDataDown(String id, String blockId, String dataId) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.getBlocks().stream()
                    .filter(b -> b.getId().equals(blockId))
                    .findFirst()
                    .ifPresent(b -> {
                        b.moveDataDown(dataId);
                    });
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout moveBlockDataTop(String id, String blockId, String dataId) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.getBlocks().stream()
                    .filter(b -> b.getId().equals(blockId))
                    .findFirst()
                    .ifPresent(b -> {
                        b.moveDataTop(dataId);
                    });
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }

    public PageLayout moveBlockDataBottom(String id, String blockId, String dataId) {
        return pageLayoutRepository.findById(id)
            .map(existingPageLayout -> {
                existingPageLayout.getBlocks().stream()
                    .filter(b -> b.getId().equals(blockId))
                    .findFirst()
                    .ifPresent(b -> {
                        b.moveDataBottom(dataId);
                    });
                return pageLayoutRepository.save(existingPageLayout);
            })
            .orElseThrow();
    }
}
