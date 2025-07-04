const prisma = require('../config/db/prismaClient');

const PostService = {
    getAllPosts: async (skip, take) => {
        const [posts, total] = await Promise.all([
            prisma.post.findMany({
                skip,
                take,
                orderBy: { created_at: 'desc' },
                where: { status: 'OPENING' },
                include: {
                    Company: {
                        include: {
                            User: {
                                select: {
                                    id: true,
                                    avatar: true,
                                }
                            }
                        }
                    },
                    contents: true,
                    Reaction: {
                        include: {
                            User: {
                                select: {
                                    id: true,
                                    username: true,
                                    avatar: true
                                }
                            }
                        }
                    },
                    Comment: {
                        include: {
                            User: {
                                select: {
                                    id: true,
                                    username: true,
                                    avatar: true
                                }
                            }
                        }
                    }
                },
            }),
            prisma.post.count({
                where: { status: 'OPENING' }
            }),
        ]);
        return { posts, total };
    },


    getPostById: async (id) => {
        return prisma.post.findUnique({
            where: { id },
            include: {
                Company: {
                    include: {
                        User: {
                            select: {
                                id: true,
                                avatar: true,
                            }
                        }
                    }
                },
                contents: true,
                Reaction: {
                    include: {
                        User: {
                            select: {
                                id: true,
                                username: true,
                                avatar: true
                            }
                        }
                    }
                },
                Comment: {
                    include: {
                        User: {
                            select: {
                                id: true,
                                username: true,
                                avatar: true
                            }
                        }
                    }
                }
            },
        });
    },

    createPost: async ({ companyId, title, contents }) => {
        return prisma.post.create({
            data: {
                companyId,
                title,
                contents: {
                    create: contents.map(c => ({
                        type: c.type,
                        value: c.value,
                        order: c.order,
                    })),
                },
            },
            include: {
                Company: true,
                contents: true,
            },
        });
    },


    updatePost: async (id, data) => {
        if (!id) throw new Error('Post ID is required');

        const { title, contents } = data;

        try {
            const result = await prisma.$transaction([
                prisma.post.update({
                    where: { id },
                    data: { title },
                }),

                prisma.postContent.deleteMany({
                    where: { postId: id },
                }),

                ...(Array.isArray(contents) && contents.length > 0
                    ? [prisma.postContent.createMany({
                        data: contents.map(c => ({
                            postId: id,
                            type: c.type,
                            value: c.value,
                            order: c.order,
                        })),
                    })]
                    : [])
            ]);

            return result[0]; // post đã update
        } catch (error) {
            throw new Error(`Error updating post: ${error.message}`);
        }
    },


    deletePost: async (id) => {
        return prisma.post.delete({ where: { id } });
    },

    searchPosts: async (filters, skip, take) => {
        const orConditions = [];

        if (filters.title) {
            orConditions.push({
                title: {
                    contains: filters.title.trim(),
                    mode: 'insensitive',
                },
            });
        }

        if (filters.contents) {
            orConditions.push({
                contents: {
                    some: {
                        value: {
                            contains: filters.contents.trim(),
                            mode: 'insensitive',
                        },
                    },
                },
            });
        }

        if (filters.companyName) {
            const matchedCompanies = await prisma.company.findMany({
                where: {
                    name: {
                        contains: filters.companyName.trim(),
                        mode: 'insensitive',
                    },
                },
                select: { id: true },
            });

            const companyIds = matchedCompanies.map((c) => c.id);
            if (companyIds.length > 0) {
                orConditions.push({
                    companyId: {
                        in: companyIds,
                    },
                });
            }
        }

        const where = orConditions.length > 0
            ? { AND: [{ status: 'OPENING' }, { OR: orConditions }] }
            : { status: 'OPENING' };


        const [posts, total] = await Promise.all([
            prisma.post.findMany({
                where,
                skip,
                take,
                orderBy: { created_at: 'desc' },
                include: {
                    Company: {
                        include: {
                            User: {
                                select: {
                                    id: true,
                                    avatar: true,
                                }
                            }
                        }
                    },
                    contents: true,
                    Reaction: {
                        include: {
                            User: {
                                select: {
                                    id: true,
                                    username: true,
                                    avatar: true
                                }
                            }
                        }
                    },
                    Comment: {
                        include: {
                            User: {
                                select: {
                                    id: true,
                                    username: true,
                                    avatar: true
                                }
                            }
                        }
                    }
                },
            }),
            prisma.post.count({ where }),
        ]);

        return { posts, total };
    },


    getPostsByCompany: async (companyId, skip = 0, take = 10) => {
        if (!companyId) throw new Error('Company ID is required');
        try {
            const [posts, total] = await Promise.all([
                prisma.post.findMany({
                    where: { companyId, status: 'OPENING' },
                    skip,
                    take,
                    orderBy: { created_at: 'desc' },
                    include: {
                        Company: {
                            include: {
                                User: {
                                    select: {
                                        id: true,
                                        avatar: true,
                                    }
                                }
                            }
                        },
                        contents: true,
                        Reaction: {
                            include: {
                                User: {
                                    select: {
                                        id: true,
                                        username: true,
                                        avatar: true
                                    }
                                }
                            }
                        },
                        Comment: {
                            include: {
                                User: {
                                    select: {
                                        id: true,
                                        username: true,
                                        avatar: true
                                    }
                                }
                            }
                        }
                    },
                }),
                prisma.post.count({
                    where: { companyId },
                }),
            ]);
            return { posts, total };
        } catch (error) {
            throw new Error(`Error fetching posts by company: ${error.message}`);
        }
    },
    getPostsByStatus: async (status, skip = 0, take = 10) => {
        if (!status) throw new Error('Status is required');

        try {
            const where = { status: status };

            const [posts, total] = await Promise.all([
                prisma.post.findMany({
                    where,
                    skip,
                    take,
                    orderBy: { created_at: 'desc' },
                    include: {
                        Company: {
                            include: {
                                User: {
                                    select: {
                                        id: true,
                                        avatar: true,
                                    }
                                }
                            }
                        },
                        contents: true,
                        Reaction: {
                            include: {
                                User: {
                                    select: {
                                        id: true,
                                        username: true,
                                        avatar: true
                                    }
                                }
                            }
                        },
                        Comment: {
                            include: {
                                User: {
                                    select: {
                                        id: true,
                                        username: true,
                                        avatar: true
                                    }
                                }
                            }
                        }
                    },
                }),
                prisma.post.count({ where }),
            ]);

            return { posts, total };
        } catch (error) {
            throw new Error(`Error fetching posts by status: ${error.message}`);
        }
    },
    updatePostStatus: async (id, status) => {
        if (!id) throw new Error('Post ID is required');
        if (!status) throw new Error('Status is required');

        const validStatuses = ['OPENING', 'TERMINATED', 'CANCELLED', 'NOT_EXIST'];
        if (!validStatuses.includes(status)) {
            throw new Error('Invalid status');
        }

        try {
            const updatedPost = await prisma.post.update({
                where: { id },
                data: { status: status },   // cập nhật trường status
                include: {
                    Company: true,
                    contents: true,
                },
            });
            return updatedPost;
        } catch (error) {
            throw new Error(`Error updating post status: ${error.message}`);
        }
    },

};

module.exports = { PostService };
