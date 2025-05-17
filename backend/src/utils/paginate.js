function getPagination(page = 1, pageSize = 10) {
  const skip = (page - 1) * pageSize;
  return { skip, take: pageSize };
}

function buildMeta(total, page, pageSize) {
  return {
    total,
    page,
    pageSize,
    totalPages: Math.ceil(total / pageSize),
  };
}

module.exports = { getPagination, buildMeta };