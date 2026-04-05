import ast

class LoopVisitor(ast.NodeVisitor):
    def __init__(self):
        self.depth = 0
        self.max_depth = 0
        self.loop_count = 0

    def visit_For(self, node):
        self.loop_count += 1
        self.depth += 1
        self.max_depth = max(self.max_depth, self.depth)
        self.generic_visit(node)
        self.depth -= 1

    def visit_While(self, node):
        self.loop_count += 1
        self.depth += 1
        self.max_depth = max(self.max_depth, self.depth)
        self.generic_visit(node)
        self.depth -= 1